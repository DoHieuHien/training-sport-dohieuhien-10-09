package com.dohieuhien.shop.trainingsport.service;

import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.document.Match;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueOption;
import com.dohieuhien.shop.trainingsport.dto.respond.LeagueRow;
import com.dohieuhien.shop.trainingsport.dto.respond.MatchRow;
import com.dohieuhien.shop.trainingsport.dto.respond.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QueryService {

    PageResponse<LeagueRow> league(int page, int size);
    List<LeagueOption> leagueOptions();
    List<League> allLeagues();

    PageResponse<MatchRow> matches(String leagueShortcut, String leagueSeason, int page, int size);
    List<MatchRow> allMatches(String leagueShortcut, String leagueSeason);
}
