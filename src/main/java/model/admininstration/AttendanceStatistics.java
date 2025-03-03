package model.admininstration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.attendance.PenaltyStatus;
import model.attendance.Attendance;
import model.attendance.AttendanceStatistic;
import model.attendance.Crew;

public class AttendanceStatistics {
    private final Map<Crew, AttendanceStatistic> statistics;

    public static AttendanceStatistics from(Map<Crew, List<Attendance>> attendances) {
        Map<Crew, AttendanceStatistic> statistics = new HashMap<>();
        attendances.keySet().forEach(crew -> {
            statistics.put(crew, AttendanceStatistic.from(attendances.get(crew)));
        });
        return new AttendanceStatistics(statistics);
    }

    public AttendanceStatistics(Map<Crew, AttendanceStatistic> statistics) {
        this.statistics = statistics;
    }

    public Map<Crew, AttendanceStatistic> findPenaltyTargets() {
        return statistics.entrySet().stream()
                .filter(entrySet -> entrySet.getValue().getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toMap(entrySet -> entrySet.getKey(), entrySet -> entrySet.getValue()));
    }
}
