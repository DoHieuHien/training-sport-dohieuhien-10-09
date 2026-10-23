package com.dohieuhien.shop.trainingsport.service;

import com.dohieuhien.shop.trainingsport.dto.respond.ImportResult;
import org.springframework.stereotype.Service;

public interface ImportService {
    ImportResult importLeagues();

    ImportResult importMatches(String leagueShortcut, String leagueSeason);
}
