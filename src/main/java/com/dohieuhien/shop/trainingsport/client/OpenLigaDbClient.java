package com.dohieuhien.shop.trainingsport.client;

import com.dohieuhien.shop.trainingsport.dto.LeagueApi;
import com.dohieuhien.shop.trainingsport.dto.MatchApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "openligadb", url = "${openligadb.base-url}")
public interface OpenLigaDbClient  {

    @GetMapping("/getavailableleagues")
    List<LeagueApi> getAvailableLeagues();

    @GetMapping("/getmatchdata/{leagueShortcut}/{leagueSeason}")
    List<MatchApi> getMatchData(@PathVariable("leagueShortcut") String leagueShortcut,
                                   @PathVariable("leagueSeason") String leagueSeason);
}
