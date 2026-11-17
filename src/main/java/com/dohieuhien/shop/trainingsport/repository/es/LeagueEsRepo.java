package com.dohieuhien.shop.trainingsport.repository.es;

import com.dohieuhien.shop.trainingsport.document.elasticSearch.LeagueEsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LeagueEsRepo extends ElasticsearchRepository<LeagueEsDocument, Long> {
}
