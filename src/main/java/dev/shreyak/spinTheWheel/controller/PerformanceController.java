package dev.shreyak.spinTheWheel.controller;


import dev.shreyak.spinTheWheel.service.PerformanceSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PerformanceController {

    @Autowired
    private PerformanceSummaryService performanceSummaryService;

    @GetMapping("/performance-summary")
    public String getPerformanceSummaryAllStadiums(@RequestParam String batsman, @RequestParam String bowler) {
        return performanceSummaryService.getPerformanceSummaryAllStadiums(batsman, bowler);
    }
}