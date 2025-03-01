package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class StatusStatistics {

    private final Map<AttendanceStatus, Integer> statusStatistics;

    public StatusStatistics(List<Attendance> attendances, LocalDate today) {
        this.statusStatistics = calculate(attendances, today);
    }

    private Map<AttendanceStatus, Integer> calculate(List<Attendance> attendances, LocalDate today) {
        Map<AttendanceStatus, Integer> statistics = new EnumMap<>(AttendanceStatus.class);
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(today.getYear(), today.getMonthValue(), day);
            if (DateUtil.isWeekend(currentDate) || Holiday.isHoliday(currentDate)) {
                continue;
            }

            calculateStatistics(statistics, attendances, currentDate);
        }
        return statistics;
    }

    private void calculateStatistics(Map<AttendanceStatus, Integer> statistics, List<Attendance> attendances, LocalDate currentDate) {
        AttendanceStatus status = attendances.stream()
                .filter(attendance -> attendance.isSameDate(currentDate))
                .map(Attendance::determineStatus)
                .findFirst()
                .orElse(AttendanceStatus.ABSENT);

        statistics.put(status, statistics.getOrDefault(status, 0) + 1);
    }

    public int getAttendanceStatusCount(AttendanceStatus status) {
        return statusStatistics.getOrDefault(status, 0);
    }
}
