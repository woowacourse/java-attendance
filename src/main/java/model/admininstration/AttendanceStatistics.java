package model.admininstration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Crew, AttendanceStatistic> penaltyTargets = new HashMap<>();
        statistics.keySet().stream()
                .filter(crew -> statistics.get(crew).getPenaltyStatus() != PenaltyStatus.NONE)
                .forEach(crew -> {
                    penaltyTargets.put(crew, statistics.get(crew));
                });//TODO : 스트림 통합 가능? 아니면 entryset으로?
        return penaltyTargets;
    }
}
