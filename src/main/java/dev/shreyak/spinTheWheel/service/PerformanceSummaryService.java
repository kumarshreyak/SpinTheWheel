package dev.shreyak.spinTheWheel.service;

public interface PerformanceSummaryService {

    public String getPerformanceSummary(String batsman, String bowler, String stadium);

    public String getPerformanceSummaryAllStadiums(String batsman, String bowler);
}
