package com.dohieuhien.shop.trainingsport.controller;


import com.dohieuhien.shop.trainingsport.dto.respond.*;
import com.dohieuhien.shop.trainingsport.service.ExcelExporter;
import com.dohieuhien.shop.trainingsport.service.ImportService;
import com.dohieuhien.shop.trainingsport.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/hien/sport")
@RequiredArgsConstructor
public class SportController {

    private static final MediaType XLSX =
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final ImportService importService;
    private final QueryService queryService;
    private final ExcelExporter excel;

    @PostMapping("/leagues/import")
    public ImportResult importLeagues() {
        return importService.importLeagues();
    }
    @PostMapping("/matches/import")
    public ImportResult importMatches(@RequestParam String leagueShortcut,
                                      @RequestParam String leagueSeason) {
        return importService.importMatches(leagueShortcut, leagueSeason);
    }

    @GetMapping("/leagues")
    public PageResponse<LeagueRow> leagues(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return queryService.league(page, size);
    }

    @GetMapping("/leagues/options")
    public List<LeagueOption> leagueOptions() {
        return queryService.leagueOptions();
    }

    @GetMapping("/leagues/export")
    public ResponseEntity<byte[]> exportLeagues() {
        List<List<Object>> rows = queryService.allLeagues().stream()
                .map(l -> Arrays.<Object>asList(l.getLeagueId(), l.getLeagueName(), l.getLeagueShortcut(),
                        l.getLeagueSeason(), l.getSportName(), String.valueOf(l.getLastImportedAt())))
                .toList();
        byte[] file = excel.toXlsx("Leagues",
                List.of("League ID", "Tên giải", "Shortcut", "Mùa", "Môn thể thao", "Import lần cuối"), rows);
        return xlsx(file, "leagues.xlsx");
    }

    @GetMapping("/matches")
    public PageResponse<MatchRow> matches(@RequestParam(required = false) String leagueShortcut,
                                          @RequestParam(required = false) String leagueSeason,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return queryService.matches(leagueShortcut, leagueSeason, page, size);
    }

    @GetMapping("/matches/export")
    public ResponseEntity<byte[]> exportMatches(@RequestParam(required = false) String leagueShortcut,
                                                @RequestParam(required = false) String leagueSeason) {
        List<List<Object>> rows = queryService.allMatches(leagueShortcut, leagueSeason).stream()
                .map(m -> Arrays.<Object>asList(m.matchId(), m.leagueShortcut(), m.leagueSeason(),
                        m.matchDateTime(), m.groupName(), m.team1(), m.team2(), m.score(),
                        Boolean.TRUE.equals(m.finished()) ? "Đã kết thúc" : "Chưa kết thúc"))
                .toList();
        byte[] file = excel.toXlsx("Matches",
                List.of("Match ID", "Giải", "Mùa", "Thời gian", "Vòng", "Đội 1", "Đội 2", "Tỉ số", "Trạng thái"), rows);
        return xlsx(file, "matches.xlsx");
    }

    private ResponseEntity<byte[]> xlsx(byte[] file, String name) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name)
                .contentType(XLSX)
                .body(file);
    }

}
