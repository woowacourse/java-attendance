package attendance.domain;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record AttendanceHistory(Map<LocalDate, Optional<Attendance>> history) {

    public static AttendanceHistory of(Map<LocalDate, Attendance> attendances, List<LocalDate> workingDays) {
        Map<LocalDate, Optional<Attendance>> history = new HashMap<>();
        for (LocalDate workingDay : workingDays) {
            history.put(workingDay, Optional.ofNullable(attendances.get(workingDay)));
        }
        return new AttendanceHistory(history);
    }

    public EnumMap<AttendanceStatus, Integer> countStatusOnHistory() {
        EnumMap<AttendanceStatus, Integer> statistics = new EnumMap<>(AttendanceStatus.class);
        for (LocalDate localDate : history.keySet()) {
            AttendanceStatus status = history.get(localDate)
                .map(Attendance::attendanceStatus)
                .orElse(AttendanceStatus.ABSENCE);

            statistics.put(status, statistics.getOrDefault(status, 0) + 1);
        }
        return statistics;
    }
}

