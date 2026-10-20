package com.dohieuhien.shop.trainingsport.repository;

import com.dohieuhien.shop.trainingsport.document.League;
import com.dohieuhien.shop.trainingsport.dto.LeagueApi;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface LeagueRepo extends MongoRepository<League, Long> {
    boolean existsByLeagueShortcutAndLeagueSeason(String leagueShortcut, String leagueSeason);
}
