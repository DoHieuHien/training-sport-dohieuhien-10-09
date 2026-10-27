package com.dohieuhien.shop.trainingsport.service.impl;

import com.dohieuhien.shop.trainingsport.client.OpenLigaDbClient;
import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.document.Match;
import com.dohieuhien.shop.trainingsport.dto.LeagueApi;
import com.dohieuhien.shop.trainingsport.dto.MatchApi;
import com.dohieuhien.shop.trainingsport.dto.respond.ImportResult;
import com.dohieuhien.shop.trainingsport.repository.LeagueRepo;
import com.dohieuhien.shop.trainingsport.repository.MatchRepo;
import com.dohieuhien.shop.trainingsport.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    @Autowired
    private OpenLigaDbClient client;

    @Autowired
    private LeagueRepo leagueRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Override
    public ImportResult importLeagues() {
        List<LeagueApi> data = Optional.ofNullable(client.getAvailableLeagues()).orElse(List.of());

        Map<Long, LeagueApi> map = new HashMap<>();
        for (LeagueApi a : data){
            if(a.leagueId() != null) map.put(a.leagueId(), a);
        }
        Map<Long, League> existing = leagueRepo.findAllById(map.keySet()).stream()
                .collect(Collectors.toMap(League::getLeagueId, Function.identity()));

        Instant now = Instant.now();
        int inserted = 0, updated = 0;
        List<League> docs = new ArrayList<>();
        for (LeagueApi d : map.values()) {
            League old = existing.get(d.leagueId());
            if (old == null) inserted++; else updated++;
            docs.add(League.builder()
                    .leagueId(d.leagueId())
                    .leagueName(d.leagueName())
                    .leagueShortcut(d.leagueShortcut())
                    .leagueSeason(d.leagueSeason())
                    .sportId(d.sport() != null ? d.sport().sportId() : null)
                    .sportName(d.sport() != null ? d.sport().sportName() : null)
                    .firstImportedAt(old != null ? old.getFirstImportedAt() : now)
                    .lastImportedAt(now)
                    .build());
        }
        leagueRepo.saveAll(docs);
        return new ImportResult("leagues", data.size(), inserted, updated);
    }

    @Override
    public ImportResult importMatches(String leagueShortcut, String leagueSeason) {
        check(leagueShortcut, leagueSeason);
        if(!leagueRepo.existsByLeagueShortcutAndLeagueSeason(leagueShortcut, leagueSeason)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "League" + leagueShortcut + "Season" + leagueSeason + "empty data, import first");
        }

        List<MatchApi> data = Optional.ofNullable(client.getMatchData(leagueShortcut, leagueSeason)).orElse(List.of());

        Map<Long, MatchApi> mapM = new LinkedHashMap<>();
        for (MatchApi m : data){
            if(m.matchID() != null) mapM.put(m.matchID(), m);
        }
        Map<Long, Match> existM = matchRepo.findAllById(mapM.keySet()).stream().collect(Collectors.toMap(Match::getMatchId, Function.identity()));

        Instant now = Instant.now();
        int inserted = 0, update = 0;
        List<Match> docs = new ArrayList<>();
        for(MatchApi m : mapM.values()){
            Match old = existM.get(m.matchID());
            if(old == null) inserted++; else update++;
            docs.add(Match.builder()
                    .matchId(m.matchID())
                    .leagueId(m.leagueId())
                    .leagueName(m.leagueName())
                    .leagueShortcut(leagueShortcut)
                    .leagueSeason(leagueSeason)
                    .matchDateTime(m.matchDateTime())
                    .matchDateTimeUTC(m.matchDateTimeUTC())
                    .matchIsFinished(m.matchIsFinished())
                    .group(m.group())
                    .team1(m.team1())
                    .team2(m.team2())
                    .matchResults(m.matchResults())
                    .goals(m.goals())
                    .location(m.location())
                    .numberOfViewers(m.numberOfViewers())
                    .lastUpdateDateTime(m.lastUpdateDateTime())
                    .firstImportedAt(old != null ? old.getFirstImportedAt() : now)
                    .lastImportedAt(now)
                    .build());
        }

        matchRepo.saveAll(docs);
        return new ImportResult("matches", data.size(), inserted, update);
    }

    private void check(String leagueShortcut, String leagueSeason) {
        if (leagueShortcut == null || !leagueShortcut.matches("[A-Za-z0-9_]{1,30}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "leagueShortcut not available");

        }
        if (leagueSeason == null || !leagueSeason.matches("\\d{4}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "leagueSeason not available");
        }
    }
}
