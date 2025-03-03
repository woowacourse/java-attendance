package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class DefaultAttendanceStatistics implements AttendanceStatistics {

    private final LocalDateProvider dateProvider;
    private final AttendanceChecker checker;

    public DefaultAttendanceStatistics(LocalDateProvider dateProvider, AttendanceChecker checker) {
        this.dateProvider = dateProvider;
        this.checker = checker;
    }

    @Override
    public Map<AttendanceStatus, Integer> getTotalStatusCount(Attendances attendances) {
        Map<AttendanceStatus, Integer> statusCount = new EnumMap<>(AttendanceStatus.class);
        Map<LocalDate, Attendance> attendanceTimeStamp = attendances.getAttendances();
        List<Attendance> monthlyAttendances = extractValidAttendances(attendanceTimeStamp);
        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> statusCount.put(status, getStatusCount(status, monthlyAttendances)));

        statusCount.put(ABSENCE, statusCount.get(ABSENCE) + countBlankAttendance(attendanceTimeStamp));
        return statusCount;
    }

    private List<Attendance> extractValidAttendances(Map<LocalDate, Attendance> attendanceTimeStamp) {
        LocalDate now = dateProvider.now();
        LocalDate month = LocalDate.of(now.getYear(), now.getMonthValue(), 1).minusDays(1);

        return attendanceTimeStamp.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(month))
                .filter(entry -> entry.getKey().isBefore(now))
                .map(Entry::getValue)
                .toList();
    }

    private int getStatusCount(AttendanceStatus status, List<Attendance> attendances) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.status() == status)
                .count();
    }

    private int countBlankAttendance(Map<LocalDate, Attendance> attendances) {
        LocalDate now = dateProvider.now();
        return (int) IntStream.range(1, dateProvider.now().getDayOfMonth())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !attendances.containsKey(date))
                .filter(date -> checker.isCampusOpenDate(date))
                .count();
    }
}
