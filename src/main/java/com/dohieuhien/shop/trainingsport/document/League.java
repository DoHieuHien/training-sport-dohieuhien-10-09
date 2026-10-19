package com.dohieuhien.shop.trainingsport.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "league")
public class League {
    @Id
    private Long leagueId;
    private String leagueName;
    private String leagueShortcut;
    private String leagueSeason;
    private Integer sportId;
    private String sportName;
    private Instant firstImportedAt;
    private Instant lastImportedAt;
}
