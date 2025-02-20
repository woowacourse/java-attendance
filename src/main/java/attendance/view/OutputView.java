package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;

import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceRepository;
import attendance.domain.AttendanceStatus;
import attendance.domain.HourMinute;
import attendance.domain.WarningLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {
    private static final String ATTENDANCE_RESULT_FORMAT = "%02d월 %02d일 %s %s (%s)";
    private static final String MODIFY_SUCCESS_FORMAT = "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!";
    private static final String QUERY_ATTENDANCE_HEADER_FORMAT = "이번 달 %s의 출석 기록입니다.";
    private static final String ATTENDANCE_STATUS_FORMAT = "%s: %d회";
    private static final String ABSENCE_TIME_FORMAT = "--:--";
    private static final String WARNING_FORMAT = "%s 대상자입니다.";
    private static final String WARNING_CREW_HEADER_FORMAT = "제적 위험자 조회 결과";
    private static final String WARNING_CREW_RESULT_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)";
    private static final String OPERATION_OPTION_MESSAGE =
            """
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료""";

    private static final String TODAY_IS = "오늘은 %s입니다. 기능을 선택해 주세요.";

    public static void printOptions() {
        System.out.printf(TODAY_IS, convertDate(LocalDateTime.now()));
        System.out.println();
        System.out.println(OPERATION_OPTION_MESSAGE);
    }

    public static void printAddedAttendance(LocalDateTime localDateTime) {
        System.out.println();
        LocalTime time = localDateTime.toLocalTime();
        System.out.printf(ATTENDANCE_RESULT_FORMAT,
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                time.toString(),
                AttendanceChecker.checkAttendance(localDateTime).getStatus());
    }

    private static String convertAttendanceResult(final LocalDateTime localDateTime) {
        return String.format("%s (%s)", convertDateTime(localDateTime),
                AttendanceChecker.checkAttendance(localDateTime).getStatus());
    }

    private static String convertDate(LocalDateTime localDateTime) {
        return String.format("%02d월 %02d일 %s", localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private static String convertDateTime(LocalDateTime localDateTime) {
        return String.format("%s %s", convertDate(localDateTime), localDateTime.toLocalTime().toString());
    }

    public static void printModifiedAttendance(HourMinute prevHourMinute, LocalDateTime newAttendanceTime) {
        LocalTime prevTime = LocalTime.of(prevHourMinute.hour(), prevHourMinute.minute());
        System.out.printf(MODIFY_SUCCESS_FORMAT,
                newAttendanceTime.getMonthValue(),
                newAttendanceTime.getDayOfMonth(),
                newAttendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                prevTime.toString(),
                prevHourMinute.attendanceStatus().getStatus(),
                newAttendanceTime.toLocalTime().toString(),
                AttendanceChecker.checkAttendance(newAttendanceTime).getStatus());
    }

    public static void printQueryAttendance(String name, AttendanceRepository attendanceRepository) {
        int today = LocalDate.now().getDayOfMonth();
        Map<LocalDate, HourMinute> crewAttendances = attendanceRepository.queryCrewAttendance(name, today);

        System.out.println();
        System.out.printf(QUERY_ATTENDANCE_HEADER_FORMAT, name);
        System.out.println();

        for (int day = 1; day < today; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            if (!crewAttendances.containsKey(date)) {
                continue;
            }

            HourMinute hourMinute = crewAttendances.get(date);

            if (hourMinute.hour() == -1 && hourMinute.minute() == -1) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("\n").append(convertDate(LocalDateTime.of(date, LocalTime.of(0, 0))));
                stringBuilder.append(" ")
                        .append(ABSENCE_TIME_FORMAT)
                        .append(" (")
                        .append(hourMinute.attendanceStatus().getStatus())
                        .append(")");
                System.out.print(stringBuilder);
                continue;
            }
            printAddedAttendance(LocalDateTime.of(2024, 12, day, hourMinute.hour(), hourMinute.minute()));
        }
        System.out.println();
        System.out.println();
        printAttendanceStatus(name, attendanceRepository, today);
        printCrewWarningLevel(name, attendanceRepository, today);
    }

    private static void printAttendanceStatus(String name, AttendanceRepository attendanceRepository, int today) {
        Map<AttendanceStatus, Integer> statuses = attendanceRepository.queryCrewAttendanceStatus(name, today);

        statuses.keySet().forEach(status -> {
            System.out.printf(ATTENDANCE_STATUS_FORMAT, status.getStatus(), statuses.get(status));
            System.out.println();
        });
    }

    private static void printCrewWarningLevel(String name, AttendanceRepository attendanceRepository, int today) {
        WarningLevel level = attendanceRepository.queryWarningLevelByName(name, today);
        if (level == WarningLevel.NONE) {
            return;
        }
        System.out.println();
        System.out.printf(WARNING_FORMAT, level.getLevel());
        System.out.println();
    }

    public static void printWarningCrews(AttendanceRepository attendanceRepository) {
        System.out.println(WARNING_CREW_HEADER_FORMAT);

        int today = LocalDate.now().getDayOfMonth();
        Arrays.stream(WarningLevel.values()).sequential().forEach(level -> {
            List<String> names = attendanceRepository.findByWarningLevel(level, today);
            List<String> formatted = format(attendanceRepository, names, today);
            formatted.forEach(System.out::println);
        });
    }

    private static List<String> format(final AttendanceRepository attendanceRepository, final List<String> names,
                                       final int today) {
        return names.stream().map(name -> {
            final Map<AttendanceStatus, Integer> crewStatuses = attendanceRepository.queryCrewAttendanceStatus(
                    name, today);
            WarningLevel level = WarningLevel.calculateLevel(crewStatuses);

            return String.format(WARNING_CREW_RESULT_FORMAT, name, crewStatuses.get(ABSENCE),
                    crewStatuses.get(LATENESS), level.getLevel());
        }).toList();
    }

}
