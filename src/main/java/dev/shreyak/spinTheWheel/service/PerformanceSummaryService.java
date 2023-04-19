package dev.shreyak.spinTheWheel.service;

public interface PerformanceSummaryService {

    public String getPerformanceSummary(String batsman, String bowler, String stadium);

    public String getPerformanceSummaryAllStadiums(String batsman, String bowler);

    public String getPerformanceSummaryAllStadiums(String[] team1, String[] team2);

    public String getSummaryForBowler(String[] bowler, String venue, String type, String afterDate);

    public String getSummaryForBatsman(String[] batsman, String venue, String type, String afterDate);

}
