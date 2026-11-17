package com.dohieuhien.shop.trainingsport.document.elasticSearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEsDocument {

    @Id
    private Long matchId;

    @Field(type = FieldType.Keyword)
    private String leagueShortcut;

    @Field(type = FieldType.Keyword)
    private String leagueSeason;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String leagueName;

    @Field(type = FieldType.Keyword)
    private String matchDateName;

    @Field(type = FieldType.Keyword)
    private String matchDataTimeUTC;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String groupName;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String team1Name;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String tea2Name;

    @Field(type = FieldType.Keyword)
    private String score;

    @Field(type = FieldType.Boolean)
    private Boolean finished;

    @Field(type = FieldType.Keyword)
    private String lastImportedAt;
}
