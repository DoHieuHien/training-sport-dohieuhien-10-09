package com.dohieuhien.shop.trainingsport.service.impl;

import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.document.Match;
import com.dohieuhien.shop.trainingsport.dto.MatchApi;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueOption;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueRow;
import com.dohieuhien.shop.trainingsport.dto.respond.MatchRow;
import com.dohieuhien.shop.trainingsport.dto.respond.PageResponse;
import com.dohieuhien.shop.trainingsport.repository.LeagueRepo;
import com.dohieuhien.shop.trainingsport.repository.MatchRepo;
import com.dohieuhien.shop.trainingsport.service.QueryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class QueryServiceImpl implements QueryService {

    private static final Sort LEAGUE_SORT =
            Sort.by("leagueShortcut").ascending().and(Sort.by("leagueSeason").descending());

    private static final Sort MATCH_SORT = Sort.by("matchDateTimeUTC").ascending();

    @Autowired
    private LeagueRepo leagueRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Override
    public PageResponse<LeagueRow> league(int page, int size) {
        Page<League> leaguePage = leagueRepo.findAll(PageRequest.of(Math.max(page, 0), clamp(size), LEAGUE_SORT));
        return PageResponse.of(leaguePage.map(l -> new LeagueRow(
                l.getLeagueId(), l.getLeagueName(), l.getLeagueShortcut(), l.getLeagueSeason(), l.getSportName(),
                matchRepo.existsByLeagueShortcutAndLeagueSeason(l.getLeagueShortcut(), l.getLeagueSeason()), l.getLastImportedAt())));
    }


    @Override
    public List<LeagueOption> leagueOptions() {
        return leagueRepo.findAll(LEAGUE_SORT).stream()
                .map(league -> new LeagueOption(league.getLeagueId(), league.getLeagueName(), league.getLeagueShortcut(), league.getLeagueSeason()))
                .toList();
    }

    @Override
    public List<League> allLeagues() {
        return leagueRepo.findAll(LEAGUE_SORT);
    }

    @Override
    public PageResponse<MatchRow> matches(String leagueShortcut, String leagueSeason, int page, int size) {
        PageRequest pageRequest = PageRequest.of(Math.max(page, 0), clamp(size),MATCH_SORT);
        Page<Match> p = filtered(leagueShortcut, leagueSeason)
                ? matchRepo.findByLeagueShortcutAndLeagueSeason(leagueShortcut, leagueSeason, pageRequest)
                : matchRepo.findAll(pageRequest);
        return PageResponse.of(p.map(this::toRow));
    }

    @Override
    public List<MatchRow> allMatches(String leagueShortcut, String leagueSeason) {
        List<Match> docs = filtered(leagueShortcut, leagueSeason)
                ? matchRepo.findByLeagueShortcutAndLeagueSeason(leagueShortcut, leagueSeason, MATCH_SORT)
                : matchRepo.findAll(MATCH_SORT);
        return docs.stream().map(this::toRow).toList();
    }


    //======
    private int clamp(int size) {
        return Math.min(Math.max(size, 1), 100);
    }

    private MatchRow toRow(Match match) {
        return new MatchRow(match.getMatchId(), match.getLeagueShortcut(), match.getLeagueSeason(), match.getMatchDateTime(),
                match.getGroup() != null ? match.getGroup().groupName() : null,
                match.getTeam1() != null ? match.getTeam1().teamName() : null,
                match.getTeam2() != null ? match.getTeam2().teamName() : null,
                finalScore(match), match.getMatchIsFinished());
    }

    private String finalScore(Match match) {
        if(match.getMatchResults() == null || match.getMatchResults().isEmpty()) return  null;
        MatchApi.Result r = match.getMatchResults().stream()
                .filter(x -> Objects.equals(x.resultTypeID(), 2)).findFirst()
                .orElseGet(() -> match.getMatchResults().stream()
                        .max(Comparator.comparing(x -> x.resultOrderID() == null ? 0 : x.resultOrderID()))
                        .orElse(null));
        return r == null ? null : r.pointsTeam1() + " : " + r.pointsTeam2();
    }

    private boolean filtered(String leagueShortcut, String leagueSeason){
        boolean hasShortcut = leagueShortcut != null && !leagueShortcut.isBlank();
        boolean hasSeason = leagueSeason != null && !leagueSeason.isBlank();

        if (hasShortcut != hasSeason) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Need leagueShortcut and leagueSeason or nothing");
        }
        return hasShortcut;
    }
}
