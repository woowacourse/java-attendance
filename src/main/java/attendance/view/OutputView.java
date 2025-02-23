package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;

import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTimeStatus;
import attendance.domain.CrewAttendanceRepository;
import attendance.domain.WarningLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final String DATE_FORMAT = "%02d월 %02d일 %s";
    private static final String ATTENDANCE_RESULT_FORMAT = "%s %s (%s)\n";
    private static final String MODIFY_SUCCESS_FORMAT = "\n%s %s (%s) -> %s (%s) 수정 완료!\n";
    private static final String QUERY_ATTENDANCE_HEADER_FORMAT = "\n이번 달 %s의 출석 기록입니다.\n\n";
    private static final String ATTENDANCE_STATUS_FORMAT = "%s: %d회\n";
    private static final String ABSENCE_TIME_FORMAT = "--:--";
    private static final String WARNING_FORMAT = "%s 대상자입니다.\n";
    private static final String WARNING_CREW_HEADER_FORMAT = "\n제적 위험자 조회 결과\n";
    private static final String WARNING_CREW_RESULT_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    private static final String TODAY_IS = "\n오늘은 %s입니다. 기능을 선택해 주세요.\n";
    private static final String OPERATION_OPTION_MESSAGE =
            """
                    1. 출석 확인
                    2. 출석 수정
                    3. 크루별 출석 기록 확인
                    4. 제적 위험자 확인
                    Q. 종료
                    """;

    public static void printOptions() {
        System.out.printf(TODAY_IS, convertDate(LocalDateTime.now()));
        System.out.print(OPERATION_OPTION_MESSAGE);
    }

    public static void printAttendance(LocalDateTime localDateTime) {
        LocalTime time = localDateTime.toLocalTime();
        System.out.printf(ATTENDANCE_RESULT_FORMAT,
                convertDate(localDateTime),
                time.toString(),
                AttendanceChecker.checkAttendance(localDateTime).getDisplayName());
    }

    public static void printModifiedAttendance(
            AttendanceTimeStatus prevAttendanceTimeStatus,
            LocalDateTime newAttendanceTime
    ) {
        Optional<LocalTime> localTime = prevAttendanceTimeStatus.time();

        localTime.ifPresentOrElse(time ->
                        System.out.printf(MODIFY_SUCCESS_FORMAT,
                                convertDate(newAttendanceTime),
                                time,
                                prevAttendanceTimeStatus.status().getDisplayName(),
                                newAttendanceTime.toLocalTime().toString(),
                                AttendanceChecker.checkAttendance(newAttendanceTime).getDisplayName()),
                () -> System.out.printf(MODIFY_SUCCESS_FORMAT,
                        convertDate(newAttendanceTime),
                        ABSENCE_TIME_FORMAT,
                        prevAttendanceTimeStatus.status().getDisplayName(),
                        newAttendanceTime.toLocalTime().toString(),
                        AttendanceChecker.checkAttendance(newAttendanceTime).getDisplayName()));
    }

    private static String convertDate(LocalDateTime localDateTime) {
        return String.format(DATE_FORMAT,
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    public static void printQueryAttendance(String name, CrewAttendanceRepository crewAttendanceRepository) {
        int today = LocalDate.now().getDayOfMonth();
        Map<LocalDate, AttendanceTimeStatus> crewAttendances = crewAttendanceRepository.queryCrewAttendance(name,
                today);

        System.out.printf(QUERY_ATTENDANCE_HEADER_FORMAT, name);

        printCrewAttendances(today, crewAttendances);
        printAttendanceStatus(name, crewAttendanceRepository, today);
        printCrewWarningLevel(name, crewAttendanceRepository, today);
    }

    private static void printCrewAttendances(final int today,
                                             final Map<LocalDate, AttendanceTimeStatus> crewAttendances) {
        for (int day = 1; day < today; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            printAttendances(crewAttendances, date);
        }
        System.out.println();
    }

    private static void printAttendances(
            final Map<LocalDate, AttendanceTimeStatus> crewAttendances, final LocalDate date
    ) {
        if (!crewAttendances.containsKey(date)) {
            return;
        }

        AttendanceTimeStatus attendanceTimeStatus = crewAttendances.get(date);
        Optional<LocalTime> attendanceTime = attendanceTimeStatus.time();

        attendanceTime.ifPresentOrElse(time -> printAttendance(LocalDateTime.of(date, time)),
                () -> System.out.printf("%s %s (%s)\n",
                        convertDate(LocalDateTime.of(date, LocalTime.of(0, 0))),
                        ABSENCE_TIME_FORMAT,
                        attendanceTimeStatus.status().getDisplayName()));
    }

    private static void printAttendanceStatus(String name, CrewAttendanceRepository crewAttendanceRepository,
                                              int today) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts =
                crewAttendanceRepository.queryCrewAttendanceStatus(name, today);

        for (AttendanceStatus status : attendanceStatusCounts.keySet()) {
            System.out.printf(ATTENDANCE_STATUS_FORMAT, status.getDisplayName(), attendanceStatusCounts.get(status));
        }
        System.out.println();
    }

    private static void printCrewWarningLevel(String name, CrewAttendanceRepository crewAttendanceRepository,
                                              int today) {
        WarningLevel level = crewAttendanceRepository.queryWarningLevelByName(name, today);
        if (level == WarningLevel.NONE) {
            return;
        }
        System.out.printf(WARNING_FORMAT, level.getDisplayName());
    }

    public static void printWarningCrews(CrewAttendanceRepository crewAttendanceRepository) {
        System.out.print(WARNING_CREW_HEADER_FORMAT);

        int today = LocalDate.now().getDayOfMonth();
        Arrays.stream(WarningLevel.values()).sequential().forEach(level -> {
            List<String> names = crewAttendanceRepository.findByWarningLevel(level, today);
            List<String> formattedStatusCounts = formatStatusCount(crewAttendanceRepository, names, today);
            formattedStatusCounts.forEach(System.out::print);
        });
    }

    private static List<String> formatStatusCount(
            final CrewAttendanceRepository crewAttendanceRepository,
            final List<String> names,
            final int today
    ) {
        return names.stream().map(name -> {
            final Map<AttendanceStatus, Integer> crewStatuses = crewAttendanceRepository.queryCrewAttendanceStatus(
                    name, today);
            WarningLevel level = WarningLevel.calculateLevel(crewStatuses);

            return String.format(WARNING_CREW_RESULT_FORMAT, name, crewStatuses.get(ABSENCE),
                    crewStatuses.get(LATENESS), level.getDisplayName());
        }).toList();
    }
}
