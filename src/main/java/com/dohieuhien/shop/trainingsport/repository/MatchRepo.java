package com.dohieuhien.shop.trainingsport.repository;

import com.dohieuhien.shop.trainingsport.document.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface MatchRepo extends MongoRepository<Match, Long> {
    boolean existsByLeagueShortcutAndLeagueSeason(String leagueShortcut, String leagueSeason);
    Page<Match> findByLeagueShortcutAndLeagueSeason(String s, String season, Pageable pageable);
    List<Match> findByLeagueShortcutAndLeagueSeason(String s, String season, Sort sort);
}
