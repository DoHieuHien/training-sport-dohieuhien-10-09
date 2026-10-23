package com.dohieuhien.shop.trainingsport.dto.respond;

public record MatchRow(Long matchId, String leagueShortcut, String leagueSeason, String matchDateTime,
                       String groupName, String team1, String team2, String score, Boolean finished) {}
