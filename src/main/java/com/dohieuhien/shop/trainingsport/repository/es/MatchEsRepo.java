package com.dohieuhien.shop.trainingsport.repository.es;

import com.dohieuhien.shop.trainingsport.document.elasticSearch.MatchEsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MatchEsRepo extends ElasticsearchRepository <MatchEsDocument, Long> {
   boolean existsByLeagueShortcutAndLeagueSeason(String leagueShortcut,String leagueSeason);
}
