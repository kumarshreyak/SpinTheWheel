package dev.shreyak.spinTheWheel.controller;


import dev.shreyak.spinTheWheel.model.TeamPerformanceRequest;
import dev.shreyak.spinTheWheel.service.PerformanceSummaryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class PerformanceController {

    @Autowired
    private PerformanceSummaryService performanceSummaryService;

    @GetMapping("/performance/pair")
    public String getPerformanceSummaryAllStadiums(@RequestParam String batsman, @RequestParam String bowler) {
        return performanceSummaryService.getPerformanceSummaryAllStadiums(batsman, bowler);
    }

    @PostMapping("/performance/team")
    public String getTeamPerformanceSummaryAllStadiums(@Valid @RequestBody TeamPerformanceRequest request) {
        return performanceSummaryService.getPerformanceSummaryAllStadiums(request.getTeam1(), request.getTeam2());
    }
}