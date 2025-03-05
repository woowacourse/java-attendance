package domain;

import java.util.List;
import java.util.Map;

public class RiskCrewStatistics {
    private final Map<String, AttendanceStatistic> statistics;

    public RiskCrewStatistics(Map<String, AttendanceStatistic> statistics) {
        this.statistics = statistics;
    }

    public List<String> getCrewNamesByStatus(ExpulsionRiskStatus status) {
        return statistics.keySet().stream()
                .filter(crew -> statistics.get(crew).getExpulsionRiskStatus() == status)
                .toList();
    }

    public int getTotalAbsenceCount(String crew) {
        validateCrew(crew);
        AttendanceStatistic statistic = statistics.get(crew);
        return statistic.getTotalAbsenceCount();
    }

    public int getLateCount(String crew) {
        validateCrew(crew);
        AttendanceStatistic statistic = statistics.get(crew);
        return statistic.getLateCount();
    }

    public int getAbsenceCount(String crew) {
        validateCrew(crew);
        AttendanceStatistic statistic = statistics.get(crew);
        return statistic.getAbsenceCount();
    }

    private void validateCrew(String crew) {
        if (!statistics.containsKey(crew)) {
            throw new RuntimeException("");
        }
    }
}
