package com.dohieuhien.shop.trainingsport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchApi(
        Integer matchID,
        String matchDateTime,
        String timeZoneID,
        Integer leagueId,
        String leagueName,
        Integer leagueSeason,
        String leagueShortcut,
        String matchDateTimeUTC,
        Group group,
        Team team1,
        Team team2,
        String lastUpdateDateTime,
        Boolean matchIsFinished,
        List<Result> matchResults,
        List<Goal> goals,
        Location location,
        Integer numberOfViewers
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Group(
            Long groupID,
            String groupName,
            Integer groupOrderID
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Team(
            Integer teamId,
            String teamName,
            String shortName,
            String teamIconUrl,
            String teamGroupName
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location(
            Integer locationID,
            String locationCity,
            String locationStadium
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
            Integer resultID,
            String resultName,
            Integer pointsTeam1,
            Integer pointsTeam2,
            Integer resultOrderID,
            Integer resultTypeID,
            String resultTypeKind,
            String resultDescription
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Goal(
            Integer goalID,
            Integer scoreTeam1,
            Integer scoreTeam2,
            Integer matchMinute,
            Integer goalGetterID,
            String goalGetterName,
            Integer scoringTeamId,
            Boolean isPenalty,
            Boolean isOwnGoal,
            Boolean isOvertime,
            String comment
    ) {}
}
