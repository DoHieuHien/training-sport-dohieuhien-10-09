package com.dohieuhien.shop.trainingsport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record   LeagueApi(Long leagueId, String leagueName, String leagueShortcut,
                           String leagueSeason, SportApi sport) {}
