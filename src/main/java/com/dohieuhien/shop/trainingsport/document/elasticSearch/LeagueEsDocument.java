package com.dohieuhien.shop.trainingsport.document.elasticSearch;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "leagues")
@Setting(settingPath = "/es/analyzer.json")
public class LeagueEsDocument {

    @Id
    private Long leagueId;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String leagueName;

    @Field(type = FieldType.Keyword)
    private String leagueShortcut;

    @Field(type = FieldType.Keyword)
    private String leagueSeason;

    @Field(type = FieldType.Text, analyzer = "folding")
    private String sportName;

    @Field(type = FieldType.Keyword)
    private String lastImportedAt;
}
