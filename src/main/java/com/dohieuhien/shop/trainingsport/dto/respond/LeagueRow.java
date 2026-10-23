package com.dohieuhien.shop.trainingsport.dto.respond;

import java.time.Instant;

public record LeagueRow(Long leagueId, String leagueName, String leagueShortcut, String leagueSeason,
                        String sportName, boolean hasMatches, Instant lastImportedAt) {}
