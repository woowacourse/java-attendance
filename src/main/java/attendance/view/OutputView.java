package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;

import attendance.domain.Attendance;
import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceStatus;
import attendance.domain.LocalDateProvider;
import attendance.domain.WarningLevel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private static final String ATTENDANCE_DESCRIPTION_FORMAT = "%02d월 %02d일 %s %s (%s)\n";
    private static final String MODIFY_ATTENDANCE_PRINT_FORMAT = "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n";
    private static final DateTimeFormatter PRINT_ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String EMPTY_ATTENDANCE_TIME_MESSAGE = "--:--";
    private static final String EMPTY_ATTENDANCE_STATUS_MESSAGE = "--";
    private static final String ATTENDANCE_RECORD_HEADER_FORMAT = "이번 달 %s의 출석 기록입니다.\n";
    private static final String ATTENDANCE_STATUS_COUNT_FORMAT = "%s: %d회\n";
    private static final String CREW_WARNING_LEVEL_FORMAT = "%s 대상자 입니다.\n\n";
    private static final String WARNING_CREW_PRINT_HEADER = "제적 위험자 조회 결과\n";
    private static final String WARNING_CREW_PRINT_FORMAT = "- %s: %s %d회, %s %d회 (%s)\n";

    private final LocalDateProvider dateProvider;
    private final AttendanceChecker checker;

    public OutputView(LocalDateProvider dateProvider, AttendanceChecker checker) {
        this.dateProvider = dateProvider;
        this.checker = checker;
    }

    public void printCheckAttendanceResult(LocalTime enterTime) {
        LocalDate now = dateProvider.now();
        System.out.print(
                createAttendanceDescription(now, enterTime, AttendanceStatus.of(now, enterTime)));
    }

    private String createAttendanceDescription(LocalDate date, LocalTime enterTime, AttendanceStatus status) {
        return String.format(ATTENDANCE_DESCRIPTION_FORMAT,
                date.getMonthValue(),
                date.getDayOfMonth(),
                getDisplayName(date),
                formatAttendanceTime(enterTime),
                status.getStatus()
        );
    }

    public void printModifyAttendanceResult(LocalTime prevTime, LocalDate date, LocalTime modifyTime) {
        System.out.printf(MODIFY_ATTENDANCE_PRINT_FORMAT,
                date.getMonthValue(),
                date.getDayOfMonth(),
                getDisplayName(date),
                formatAttendanceTime(prevTime),
                formatAttendanceStatus(date, prevTime),
                formatAttendanceTime(modifyTime),
                formatAttendanceStatus(date, modifyTime)
        );
    }

    private String formatAttendanceTime(LocalTime time) {
        if (Optional.ofNullable(time).isEmpty()) {
            return EMPTY_ATTENDANCE_TIME_MESSAGE;
        }
        return toTimeString(time);
    }

    private String toTimeString(LocalTime time) {
        return time.format(PRINT_ATTENDANCE_TIME_FORMATTER);
    }

    private String formatAttendanceStatus(LocalDate date, LocalTime time) {
        if (Optional.ofNullable(time).isEmpty()) {
            return EMPTY_ATTENDANCE_STATUS_MESSAGE;
        }
        return AttendanceStatus.of(date, time).getStatus();
    }

    public void printAttendanceRecords(String crewName, Map<LocalDate, Attendance> crewAttendances) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(ATTENDANCE_RECORD_HEADER_FORMAT, crewName)).append("\n");
        LocalDate now = dateProvider.now();
        IntStream.range(1, now.getDayOfMonth())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> checker.isCampusOpenDate(date))
                .forEach(date -> {
                    builder.append(toAttendanceRecordString(crewAttendances, date));
                });
        System.out.println(builder);
    }

    private String toAttendanceRecordString(Map<LocalDate, Attendance> crewAttendances, LocalDate date) {
        if (crewAttendances.containsKey(date)) {
            Attendance attendance = crewAttendances.get(date);
            return createAttendanceDescription(date, attendance.time(), attendance.status());
        }
        return createAttendanceDescription(date, null, ABSENCE);
    }

    public void printAttendanceStatusCount(Map<AttendanceStatus, Integer> statusCounts) {
        StringBuilder builder = new StringBuilder();
        statusCounts.keySet()
                .forEach(status -> {
                    builder.append(String.format(ATTENDANCE_STATUS_COUNT_FORMAT, status.getStatus(),
                            statusCounts.get(status)));
                });
        System.out.println(builder);
    }

    public void printCrewWarningLevel(WarningLevel level) {
        if (level == WarningLevel.NONE) {
            return;
        }
        System.out.printf(CREW_WARNING_LEVEL_FORMAT, level.getDescription());
    }

    public void printWarningCrews(Map<String, Map<AttendanceStatus, Integer>> crewsStatusCount) {
        Map<String, Integer> totalAbsence = calculateTotalAbsence(crewsStatusCount);
        StringBuilder builder = new StringBuilder();
        builder.append(WARNING_CREW_PRINT_HEADER);
        List<Entry<String, Integer>> sortedList = totalAbsence.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        appendWarningCrew(builder, crewsStatusCount, sortedList);
        System.out.println(builder);
    }

    private static void appendWarningCrew(StringBuilder builder,
                                          Map<String, Map<AttendanceStatus, Integer>> crewsStatusCount,
                                          List<Entry<String, Integer>> sortedList) {
        sortedList.stream()
                .filter(entry -> WarningLevel.from(crewsStatusCount.get(entry.getKey())) != WarningLevel.NONE)
                .forEach(entry -> {
                    Map<AttendanceStatus, Integer> statusCount = crewsStatusCount.get(entry.getKey());
                    builder.append(String.format(WARNING_CREW_PRINT_FORMAT,
                            entry.getKey(),
                            ABSENCE.getStatus(),
                            statusCount.get(ABSENCE),
                            LATENESS.getStatus(),
                            statusCount.get(LATENESS),
                            WarningLevel.from(statusCount).getDescription()
                    ));
                });
    }

    private Map<String, Integer> calculateTotalAbsence(Map<String, Map<AttendanceStatus, Integer>> crewsStatusCount) {
        Map<String, Integer> totalAbsence = new HashMap<>();
        crewsStatusCount.entrySet()
                .forEach(entry -> {
                    Map<AttendanceStatus, Integer> statusCount = entry.getValue();
                    int absenceCount = statusCount.get(AttendanceStatus.PRESENT) +
                            WarningLevel.calculateTotalAbsenceCount(statusCount.get(LATENESS));
                    totalAbsence.put(entry.getKey(), absenceCount);
                });
        return totalAbsence;
    }


    public void printErrorMessage(Exception error) {
        System.out.println(ERROR_MESSAGE_PREFIX + error);
    }

    private static String getDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
    }
}
