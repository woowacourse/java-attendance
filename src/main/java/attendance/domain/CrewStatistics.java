package attendance.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CrewStatistics {
    private final List<CrewStatistic> crewStatistics;

    public CrewStatistics(List<CrewStatistic> crewStatistics) {
        this.crewStatistics = crewStatistics;
    }

    public CrewStatistics sortCrewStatistics() {
        List<CrewStatistic> crewsStatistics = crewStatistics.stream()
                .sorted(Comparator.comparing(CrewStatistic::getPenaltyCount)
                        .reversed()
                        .thenComparing(CrewStatistic::getCrewName))
                .toList();
        return new CrewStatistics(crewsStatistics);
    }

    public List<List<String>> crewsExpelExpectedInfo() {
        List<List<String>> crewsExpelExpectedInfo = new ArrayList<>();

        for (CrewStatistic crewStatistic : crewStatistics) {
            crewsExpelExpectedInfo.add(crewStatistic.crewExpelExpectedInfo());
        }
        return crewsExpelExpectedInfo;
    }
}
