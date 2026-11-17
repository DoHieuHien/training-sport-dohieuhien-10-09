package com.dohieuhien.shop.trainingsport.service;

import com.dohieuhien.shop.trainingsport.dto.respond.ImportResult;

public interface EsImportService {

    ImportResult importLeagueToElasticSearch();
    ImportResult importMatchesToElasticSearch();

}
