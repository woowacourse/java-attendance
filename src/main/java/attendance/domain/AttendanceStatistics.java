package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceStatistics {

    public static Map<AttendanceStatus, Integer> getTotalStatusCount(Attendances attendances, int today) {
        Map<AttendanceStatus, Integer> statusCount = new EnumMap<>(AttendanceStatus.class);
        Map<LocalDate, Attendance> attendanceMap = attendances.getAttendances();

        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> statusCount.put(status, getStatusCount(status, attendanceMap)));

        statusCount.put(ABSENCE, statusCount.get(ABSENCE) + countBlankAttendance(today, attendanceMap));

        return statusCount;
    }

    private static int getStatusCount(AttendanceStatus status, Map<LocalDate, Attendance> attendances) {
        return (int) attendances.values().stream()
                .filter(attendance -> attendance.status() == status)
                .count();
    }

    private static int countBlankAttendance(int today, Map<LocalDate, Attendance> attendances) {
        LocalDate now = LocalDate.now();
        return (int) IntStream.range(1, today)
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !attendances.containsKey(date))
                .filter(AttendanceChecker::isCampusOpenDate)
                .count();
    }
}
