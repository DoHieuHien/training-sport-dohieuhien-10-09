package com.dohieuhien.shop.trainingsport.document;

import com.dohieuhien.shop.trainingsport.dto.MatchApi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document ("matches")
public class Match {

    private Long matchId;
    private Long leagueId;
    private String leagueName;
    private String leagueShortcut;
    private String leagueSeason;
    private String matchDateTime;
    private String matchDateTimeUTC;
    private Boolean matchIsFinished;
}
