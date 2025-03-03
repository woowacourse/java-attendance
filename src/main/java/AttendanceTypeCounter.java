import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceTypeCounter {

    // TODO: 더 효율적인 방법 찾아보기, Map 래핑할 수 있는지 생각해보기
    public static Map<LocalDateTime, AttendanceType> count(LocalDate requestedDate,
                                                           List<AttendanceHistory> attendanceHistories) {
        List<AttendanceHistory> sortedHistory = sortAccordingToDate(attendanceHistories);

        LocalDate firstDayOfMonth = YearMonth.from(requestedDate).atDay(1);

        return countOfAllDate(firstDayOfMonth, requestedDate, sortedHistory);
    }


    private static List<AttendanceHistory> sortAccordingToDate(List<AttendanceHistory> attendanceHistories) {
        return attendanceHistories.stream()
                .sorted(Comparator.comparing(history -> history.getAttendAt().getDayOfMonth()))
                .toList();
    }

    private static Map<LocalDateTime, AttendanceType> countOfAllDate(LocalDate firstDayOfMonth, LocalDate requestedDate,
                                                                     List<AttendanceHistory> attendanceHistories) {
        Map<LocalDateTime, AttendanceType> attendanceTypeCount = new HashMap<>();

        for (LocalDate date = firstDayOfMonth; date.isBefore(requestedDate); date = date.plusDays(1)) {

            if (!AttendanceTimeChecker.isAttendanceRequiredDate(date)) {
                continue;
            }

            if (!contains(attendanceHistories, date)) {
                attendanceTypeCount.put(LocalDateTime.of(date, LocalTime.of(0, 0)), AttendanceType.NO_DATA);
                continue;
            }

            AttendanceHistory foundHistory = findByDate(attendanceHistories, date);
            attendanceTypeCount.put(foundHistory.getAttendAt(),
                    AttendanceType.findAttendanceTypeByDateTime(foundHistory.getAttendAt()));
        }

        return attendanceTypeCount;
    }

    private static AttendanceHistory findByDate(List<AttendanceHistory> attendanceHistories, LocalDate requestedDate) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.getAttendAt().toLocalDate().isEqual(requestedDate))
                .findAny()
                .orElse(null);
    }

    private static boolean contains(List<AttendanceHistory> attendanceHistories, LocalDate date) {
        return attendanceHistories.stream()
                .anyMatch(attendanceHistory -> attendanceHistory.getAttendAt().toLocalDate().isEqual(date));
    }
}
