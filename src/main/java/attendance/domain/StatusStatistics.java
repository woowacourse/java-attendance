package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StatusStatistics {

    private final Map<AttendanceStatus, Integer> statusStatistics;

    public StatusStatistics(Map<AttendanceStatus, Integer> statusStatistics) {
        this.statusStatistics = statusStatistics;
    }

    public void calculate(List<Attendance> attendances, LocalDate today) {
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(today.getYear(), today.getMonthValue(), day);
            if (DateUtil.isWeekend(currentDate) || Holiday.isHoliday(currentDate)) {
                continue;
            }

            calculateStatistics(attendances, currentDate);
        }
    }

    private void calculateStatistics(List<Attendance> attendances, LocalDate currentDate) {
        AttendanceStatus status = attendances.stream()
                .filter(attendance -> attendance.isSameDate(currentDate))
                .map(Attendance::determineStatus)
                .findFirst()
                .orElse(AttendanceStatus.ABSENT);

        statusStatistics.put(status, statusStatistics.getOrDefault(status, 0) + 1);
    }

    public int getAttendanceStatusCount(AttendanceStatus status) {
        return statusStatistics.getOrDefault(status, 0);
    }
}
