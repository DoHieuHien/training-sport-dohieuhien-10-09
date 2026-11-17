package com.dohieuhien.shop.trainingsport.service.impl;

import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.document.Match;
import com.dohieuhien.shop.trainingsport.document.elasticSearch.LeagueEsDocument;
import com.dohieuhien.shop.trainingsport.document.elasticSearch.MatchEsDocument;
import com.dohieuhien.shop.trainingsport.dto.respond.ImportResult;
import com.dohieuhien.shop.trainingsport.repository.LeagueRepo;
import com.dohieuhien.shop.trainingsport.repository.MatchRepo;
import com.dohieuhien.shop.trainingsport.repository.es.LeagueEsRepo;
import com.dohieuhien.shop.trainingsport.repository.es.MatchEsRepo;
import com.dohieuhien.shop.trainingsport.service.EsImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EsImportServiceImpl implements EsImportService {

    @Autowired
    private LeagueRepo leagueRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private LeagueEsRepo leagueEsRepo;

    @Autowired
    private MatchEsRepo matchEsRepo;

    @Autowired
    private ElasticsearchOperations operations;

    @Override
    public ImportResult importLeagueToElasticSearch() {
        if(leagueRepo.count() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chưa có dữ liệu leagues trong MongoDB, hãy Import Leagues trước");
        }
        ensureIndex(LeagueEsDocument.class);

        int fetched  = 0, inserted = 0, updated =0;
        Pageable pageable = PageRequest.of(0, 500, Sort.by("leagueId"));
        Page<League> page;
        do{
            page = leagueRepo.findAll(pageable);
            List<LeagueEsDocument> docs = page.getContent().stream().map(this::toEs).toList();

            int existing = count(leagueEsRepo.findAllById(
                    docs.stream().map(LeagueEsDocument::getLeagueId).toList()));
            leagueEsRepo.saveAll(docs);

            fetched += docs.size();
            updated += existing;
            inserted += docs.size() - existing;
            pageable = pageable.next();
        } while (page.hasNext());

        operations.indexOps(LeagueEsDocument.class).refresh();
        return new ImportResult("elasticsearch-leagues", fetched, inserted, updated);
    }

    @Override
    public ImportResult importMatchesToElasticSearch() {
        if(matchRepo.count() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chưa có dữ liệu matches trong MongoDB, hãy Import Matches trước");
        }
        ensureIndex(MatchEsDocument.class);

        int fetched  = 0, inserted = 0, updated =0;
        Pageable pageable = PageRequest.of(0, 500, Sort.by("matchId"));
        Page<Match> page;
        do{
            page = matchRepo.findAll(pageable);
            List<MatchEsDocument> docs = page.getContent().stream().map(this::toEs).toList();

            int existing = count(matchEsRepo.findAllById(
                    docs.stream().map(MatchEsDocument::getMatchId).toList()));
            matchEsRepo.saveAll(docs);

            fetched += docs.size();
            updated += existing;
            inserted += docs.size() - existing;
            pageable = pageable.next();
        } while (page.hasNext());

        operations.indexOps(MatchEsDocument.class).refresh();
        return new ImportResult("elasticsearch-leagues", fetched, inserted, updated);
    }
    private LeagueEsDocument toEs(League league) {
        return null;
    }
    private MatchEsDocument toEs(Match league) {
        return null;
    }

    private int count(Iterable<?> items) {
        int n =0;
        for (Object ignored : items) n++;
        return n;
    }

    private void ensureIndex(Class<?> type) {
        IndexOperations ops = operations.indexOps(type);
        if(!ops.exists()) ops.createWithMapping();
    }


}
