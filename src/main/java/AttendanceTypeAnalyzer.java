import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceTypeAnalyzer {

    private static final LocalTime FIXED_TIME_FOR_NO_DATA = LocalTime.of(0, 0);

    // TODO: Map 래핑할 수 있는지 생각해보기
    public static Map<LocalDateTime, AttendanceType> analyze(LocalDate requestedDate,
                                                             List<AttendanceHistory> attendanceHistories) {
        List<AttendanceHistory> sortedHistory = sortAccordingToDate(attendanceHistories);
        LocalDate firstDayOfMonth = YearMonth.from(requestedDate).atDay(1);

        return analyzeBeforeRequestedDate(firstDayOfMonth, requestedDate, sortedHistory);
    }

    private static List<AttendanceHistory> sortAccordingToDate(List<AttendanceHistory> attendanceHistories) {
        return attendanceHistories.stream()
                .sorted(Comparator.comparing(history -> history.getAttendAt().getDayOfMonth()))
                .toList();
    }

    private static Map<LocalDateTime, AttendanceType> analyzeBeforeRequestedDate(LocalDate firstDayOfMonth,
                                                                                 LocalDate requestedDate,
                                                                                 List<AttendanceHistory> attendanceHistories) {
        Map<LocalDateTime, AttendanceType> attendanceTypeOfDates = new HashMap<>();

        for (LocalDate date = firstDayOfMonth; date.isBefore(requestedDate); date = date.plusDays(1)) {

            if (!AttendanceTimeChecker.isAttendanceRequiredDate(date)) {
                continue;
            }

            Optional<AttendanceHistory> optionalHistory = findByDate(attendanceHistories, date);

            if (optionalHistory.isPresent()) {
                analyzeTypeWhenHistoryIsExisted(optionalHistory.get(), attendanceTypeOfDates);
                continue;
            }
            analyzeTypeWhenHistoryIsNotExisted(date, attendanceTypeOfDates);
        }

        return attendanceTypeOfDates;
    }

    private static Optional<AttendanceHistory> findByDate(List<AttendanceHistory> attendanceHistories,
                                                          LocalDate requestedDate) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.getAttendAt().toLocalDate().isEqual(requestedDate))
                .findAny();
    }

    private static void analyzeTypeWhenHistoryIsExisted(AttendanceHistory attendanceHistory,
                                                        Map<LocalDateTime, AttendanceType> attendanceTypeOfDates) {
        LocalDateTime attendAt = attendanceHistory.getAttendAt();
        attendanceTypeOfDates.put(attendAt, AttendanceType.findAttendanceTypeByDateTime(attendAt));
    }

    private static void analyzeTypeWhenHistoryIsNotExisted(LocalDate date,
                                                           Map<LocalDateTime, AttendanceType> attendanceTypeOfDates) {
        attendanceTypeOfDates.put(LocalDateTime.of(date, FIXED_TIME_FOR_NO_DATA), AttendanceType.NO_DATA);
    }
}
