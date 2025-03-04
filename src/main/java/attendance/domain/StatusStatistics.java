package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

public class StatusStatistics {

    private final Map<AttendanceStatus, Integer> statusStatistics;

    public StatusStatistics(Attendances attendances, LocalDate today) {
        this.statusStatistics = calculate(attendances, today);
    }

    private Map<AttendanceStatus, Integer> calculate(Attendances attendances, LocalDate today) {
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

    private void calculateStatistics(Map<AttendanceStatus, Integer> statistics, Attendances attendances, LocalDate currentDate) {
        Attendance attendance = attendances.findByDate(currentDate);
        AttendanceStatus status = attendance.determineStatus();

        statistics.put(status, statistics.getOrDefault(status, 0) + 1);
    }

    public int getAttendanceStatusCount(AttendanceStatus status) {
        return statusStatistics.getOrDefault(status, 0);
    }
}
