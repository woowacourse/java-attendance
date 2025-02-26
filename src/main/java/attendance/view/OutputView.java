package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.WarningLevel.NONE;

import attendance.domain.AttendanceBeforeAfter;
import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTimeStatus;
import attendance.domain.CrewAttendance;
import attendance.domain.WarningLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final String TODAY_IS = "\n\n오늘은 %s입니다. 기능을 선택해 주세요.\n";
    private static final String OPERATION_OPTION_MESSAGE = """
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    private static final String DEFAULT_TIME_FORMAT = "--:--";
    private static final String DATE_DISPLAY_FORMAT = "%02d월 %02d일 %s";
    private static final String ATTENDANCE_RESULT_FORMAT = "\n%s %s (%s)";
    private static final String MODIFY_SUCCESS_FORMAT = " %s (%s) -> %s (%s) 수정 완료!";

    private static final String QUERY_ATTENDANCE_HEADER_FORMAT = "\n이번 달 %s의 출석 기록입니다.\n";
    private static final String ATTENDANCE_STATUS_FORMAT = "\n%s: %d회";
    private static final String WARNING_LEVEL_FORMAT = "\n\n%s 대상자입니다.";

    private static final String WARNING_CREW_HEADER_FORMAT = "\n제적 위험자 조회 결과";
    private static final String WARNING_CREW_RESULT_FORMAT = "\n- %s: 결석 %d회, 지각 %d회 (%s)";

    public static void printOptions() {
        System.out.printf(TODAY_IS, getDisplayDate(LocalDate.now(ZoneId.of("Asia/Seoul"))));
        System.out.print(OPERATION_OPTION_MESSAGE);
    }

    public static void printAttendance(LocalDateTime localDateTime) {
        LocalTime time = localDateTime.toLocalTime();
        System.out.printf(ATTENDANCE_RESULT_FORMAT,
                getDisplayDate(localDateTime.toLocalDate()),
                time.toString(),
                AttendanceChecker.checkAttendance(localDateTime).getDisplayName());
    }

    private static String getDisplayDate(LocalDate localDate) {
        return String.format(DATE_DISPLAY_FORMAT,
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    public static void printModifiedResult(final LocalDate date,
                                           final AttendanceBeforeAfter attendanceBeforeAfter) {
        final Optional<LocalTime> prevTime = attendanceBeforeAfter.before().time();
        final AttendanceStatus prevStatus = attendanceBeforeAfter.before().status();

        final Optional<LocalTime> newTime = attendanceBeforeAfter.after().time();
        final AttendanceStatus newStatus = attendanceBeforeAfter.after().status();

        System.out.printf(System.lineSeparator() + getDisplayDate(date) + MODIFY_SUCCESS_FORMAT,
                formatAttendanceTime(prevTime), prevStatus.getDisplayName(),
                formatAttendanceTime(newTime), newStatus.getDisplayName());
    }

    private static String formatAttendanceTime(Optional<LocalTime> localTime) {
        return localTime.map(LocalTime::toString).orElse(DEFAULT_TIME_FORMAT);
    }

    public static void printAttendances(final String name, final Map<LocalDate, AttendanceTimeStatus> crewAttendances) {
        System.out.printf(QUERY_ATTENDANCE_HEADER_FORMAT, name);
        List<LocalDate> attendanceDays = crewAttendances.keySet().stream().sorted().toList();

        for (LocalDate attendanceDay : attendanceDays) {
            System.out.printf(ATTENDANCE_RESULT_FORMAT,
                    getDisplayDate(attendanceDay),
                    formatAttendanceTime(crewAttendances.get(attendanceDay).time()),
                    crewAttendances.get(attendanceDay).status().getDisplayName());
        }
        System.out.println();
    }

    public static void printAttendanceStatuses(Map<AttendanceStatus, Integer> attendanceStatusCounts) {
        for (AttendanceStatus status : attendanceStatusCounts.keySet()) {
            System.out.printf(ATTENDANCE_STATUS_FORMAT, status.getDisplayName(), attendanceStatusCounts.get(status));
        }
    }

    public static void printCrewWarningLevel(WarningLevel warningLevel) {
        if (warningLevel == NONE) {
            return;
        }
        System.out.printf(WARNING_LEVEL_FORMAT, warningLevel.getDisplayName());
    }

    public static void printWarningCrews(Map<WarningLevel, List<CrewAttendance>> warningCrews, LocalDate today) {
        System.out.print(WARNING_CREW_HEADER_FORMAT);

        Arrays.stream(WarningLevel.values()).sequential()
                .filter(warningLevel -> warningLevel != NONE)
                .forEach(warningLevel -> {
                    List<CrewAttendance> crewAttendances = warningCrews.get(warningLevel);
                    List<String> formattedWarningCrews = formatWarningCrews(warningLevel, crewAttendances, today);
                    formattedWarningCrews.forEach(System.out::print);
                });
    }

    private static List<String> formatWarningCrews(
            WarningLevel warningLevel,
            List<CrewAttendance> crewAttendances,
            LocalDate today
    ) {
        List<String> formattedWarningCrews = new ArrayList<>();
        for (CrewAttendance crewAttendance : crewAttendances) {
            Map<AttendanceStatus, Integer> attendanceStatusCounts =
                    crewAttendance.countAttendanceStatusBefore(today);
            formattedWarningCrews.add(String.format(WARNING_CREW_RESULT_FORMAT,
                    crewAttendance.getName(),
                    attendanceStatusCounts.get(ABSENCE),
                    attendanceStatusCounts.get(LATENESS),
                    warningLevel.getDisplayName()));
        }
        return formattedWarningCrews;
    }
}
