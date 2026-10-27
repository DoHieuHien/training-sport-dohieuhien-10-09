package com.dohieuhien.shop.trainingsport.controller;

import com.dohieuhien.shop.trainingsport.client.SportClient;
import com.dohieuhien.shop.trainingsport.service.ExcelExporter;
import com.dohieuhien.shop.trainingsport.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/hien/sport")
@RequiredArgsConstructor
public class SportController {

    @Autowired
    private ExcelExporter excel;

    private static final MediaType XLSX =
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    @Autowired
    private SportClient sportClient;
    @Autowired
    private QueryService queryService;

    @PostMapping("/leagues/import")
    public JsonNode importLeagues(){ return sportClient.importLeagues();}

    @GetMapping("/matches/import")
    public JsonNode importMatches(@RequestParam String leagueShortcut,
                                  @RequestParam String leagueSeason){
        return sportClient.importMatches(leagueShortcut, leagueSeason);
    }

    @GetMapping("/leagues")
    public JsonNode leagues(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size){
        return sportClient.leagues(page, size);
    }

    @GetMapping("/leagues/options")
    public JsonNode leagueOption(){ return sportClient.leagueOptions();}

    @GetMapping("/leagues/export")
    public ResponseEntity<byte[]> exportLeagues(){
        return xlsx(sportClient.exportLeagues().getBody(), "leagues.xlsx");
    }

    @GetMapping("/matches")
    public JsonNode matches(@RequestParam(required = false) String leagueShortcut,
                            @RequestParam(required = false) String leagueSeason,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size){
        return sportClient.matches(leagueShortcut, leagueSeason, page, size);
    }

    @GetMapping("/matches/export")
    public ResponseEntity<byte[]> exportMatches(@RequestParam(required = false) String leagueShortcut,
                                                @RequestParam(required = false) String leagueSeason){
        List<List<Object>> rows = queryService.allMatches(leagueShortcut, leagueSeason).stream()
                .map(m -> Arrays.<Object>asList(m.matchId(), m.leagueShortcut(), m.leagueSeason(),
                        m.matchDateTime(), m.groupName(), m.team1(), m.team2(), m.score(),
                        Boolean.TRUE.equals(m.finished()) ? "Đã kết thúc" : "Chưa kết thúc"))
                .toList();
        byte[] file = excel.toXlsx("Matches",
                List.of("Match ID", "Giải", "Mùa", "Thời gian", "Vòng", "Đội 1", "Đội 2", "Tỉ số", "Trạng thái"), rows);
        return xlsx(file, "matches.xlsx");
    }
    private ResponseEntity<byte[]> xlsx(byte[] body, String name) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name)
                .contentType(XLSX)
                .body(body);
    }


}
