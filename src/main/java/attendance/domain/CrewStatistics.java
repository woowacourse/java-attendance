package attendance.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CrewStatistics {
    private final List<CrewStatistic> crewStatistics;

    public CrewStatistics() {
        crewStatistics = new ArrayList<>();
    }

    public CrewStatistics(List<CrewStatistic> crewStatistics) {
        this.crewStatistics = crewStatistics;
    }

    public CrewStatistics generateCrewStatistics(final CrewNames crewNames, final Attendances attendances) {
        for (CrewName crewName : crewNames.getCrewNames()) {
            Attendances crewAttendances = attendances.lookupCrewAttendance(crewName);
            CrewStatistic crewStatistic = new CrewStatistic(crewName);
            crewStatistic.checkAttendanceStatistic(crewAttendances);
            crewStatistics.add(crewStatistic);
        }
        return new CrewStatistics(sortCrewStatistics());
    }

    private List<CrewStatistic> sortCrewStatistics() {
        return crewStatistics.stream()
                .sorted(Comparator.comparing(CrewStatistic::getCrewPenalty).reversed()
                        .thenComparing(CrewStatistic::getCrewName))
                .toList();
    }

    public List<CrewStatistic> getCrewStatistics() {
        return crewStatistics;
    }
}
