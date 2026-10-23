package com.dohieuhien.shop.trainingsport.service.impl;

import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.document.Match;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueOption;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueRow;
import com.dohieuhien.shop.trainingsport.dto.respond.MatchRow;
import com.dohieuhien.shop.trainingsport.dto.respond.PageResponse;
import com.dohieuhien.shop.trainingsport.service.QueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryServiceImpl implements QueryService {


    @Override
    public PageResponse<LeagueRow> league(int page, int size) {
        return null;
    }

    @Override
    public List<LeagueOption> leagueOptions() {
        return List.of();
    }

    @Override
    public List<League> allLeagues() {
        return List.of();
    }

    @Override
    public PageResponse<MatchRow> matches(String leagueShortcut, String leagueSeason, int page, int size) {
        return null;
    }

    @Override
    public List<Match> allMatches(String leagueShortcut, String leagueSeason) {
        return List.of();
    }
}
