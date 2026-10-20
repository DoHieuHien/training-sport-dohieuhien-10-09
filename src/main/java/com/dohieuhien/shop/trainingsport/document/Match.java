package com.dohieuhien.shop.trainingsport.document;

import com.dohieuhien.shop.trainingsport.dto.MatchApi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "matches")
@CompoundIndex(name = "league_season_idx", def = "{'leagueShortcut': 1, 'leagueSeason': 1}"
)
public class Match {

    @Id
    private Long matchId;

    private Long leagueId;
    private String leagueName;
    private String leagueShortcut;
    private Integer leagueSeason;

    private String matchDateTime;
    private String timeZoneID;
    private String matchDateTimeUTC;
    private String lastUpdateDateTime;

    private Boolean matchIsFinished;

    private MatchApi.Group group;
    private MatchApi.Team team1;
    private MatchApi.Team team2;

    private List<MatchApi.Result> matchResults;
    private List<MatchApi.Goal> goals;

    private MatchApi.Location location;
    private Integer numberOfViewers;

    private Instant firstImportedAt;
    private Instant lastImportedAt;
}
