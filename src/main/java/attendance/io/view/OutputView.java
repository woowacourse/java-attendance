package attendance.io.view;

import static attendance.domain.AttendanceStatus.ABSENT;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceStatus;
import attendance.domain.WarningLevel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OutputView {

    public void printAttendance(Attendance attendance) {
        printEmptyLine();
        System.out.println(toAttendanceFullFormat(attendance));
    }

    private void printEmptyLine() {
        System.out.println();
    }

    public void printUpdatedAttendance(Optional<Attendance> pastAttendance, Attendance currentAttendance) {
        pastAttendance.ifPresentOrElse(
                attendance -> System.out.printf("%s -> %s 수정 완료!\n",
                        toAttendanceFullFormat(attendance),
                        toAttendanceShortFormat(currentAttendance)),
                () -> System.out.printf("%s -> %s 수정 완료!\n",
                        toAbsentFullFormat(currentAttendance.getAttendanceDateTime().toLocalDate()),
                        toAttendanceShortFormat(currentAttendance))
        );
    }

    public void printAttendanceResult(List<Attendance> crewAttendances,
                                      AttendanceResult attendanceResult,
                                      LocalDate endDate) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceResult.getNickname());
        printEmptyLine();

        Map<LocalDate, Attendance> attendanceMap = crewAttendances.stream()
                .collect(Collectors.toMap(Attendance::getAttendanceDate, Function.identity()));

        printAllAttendances(endDate, attendanceMap);
        printEmptyLine();
        printAttendanceStatus(attendanceResult);
        printEmptyLine();
        printWarningLevel(attendanceResult.getWarningLevel());
    }

    public void printAttendanceResults(List<AttendanceResult> attendanceResults) {
        System.out.println("제적 위험자 조회 결과");
        Collections.sort(attendanceResults);
        for (AttendanceResult attendanceResult : attendanceResults) {
            if (attendanceResult.getWarningLevel() == WarningLevel.NONE) {
                continue;
            }
            Map<AttendanceStatus, Integer> attendanceStatus = attendanceResult.getAttendanceStatus();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    attendanceResult.getNickname(),
                    attendanceStatus.getOrDefault(ABSENT, 0),
                    attendanceStatus.getOrDefault(LATE, 0),
                    toKoreaWarningLevel(attendanceResult.getWarningLevel()));
        }
    }


    private void printAttendanceStatus(AttendanceResult attendanceResult) {
        Map<AttendanceStatus, Integer> attendanceStatus = attendanceResult.getAttendanceStatus();
        System.out.printf("%s: %d회\n",
                toKoreaAttendanceStatus(ATTENDANCE),
                attendanceStatus.getOrDefault(ATTENDANCE, 0));
        System.out.printf("%s: %d회\n", toKoreaAttendanceStatus(LATE), attendanceStatus.getOrDefault(LATE, 0));
        System.out.printf("%s: %d회\n", toKoreaAttendanceStatus(ABSENT), attendanceStatus.getOrDefault(ABSENT, 0));
    }

    private void printAllAttendances(LocalDate endDate, Map<LocalDate, Attendance> attendanceMap) {
        AttendanceDate currentDate = AttendanceDate.ATTENDANCE_START_DATE;
        while (currentDate.isBeforeAndEqual(endDate)) {
            if (attendanceMap.containsKey(currentDate.getAttendanceDate())) {
                System.out.println(toAttendanceFullFormat(attendanceMap.get(currentDate.getAttendanceDate())));
                currentDate = currentDate.nextDate();
                continue;
            }
            System.out.println(toAbsentFullFormat(currentDate.getAttendanceDate()));
            currentDate = currentDate.nextDate();
        }
    }

    private void printWarningLevel(WarningLevel warningLevel) {
        if (warningLevel == WarningLevel.NONE) {
            return;
        }
        System.out.printf("%s 대상자입니다.\n", toKoreaWarningLevel(warningLevel));
    }

    private String toAttendanceFullFormat(Attendance attendance) {
        return String.format("%s (%s)",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                toKoreaAttendanceStatus(attendance.getAttendanceStatus()));
    }

    private String toAttendanceShortFormat(Attendance attendance) {
        return String.format("%s (%s)",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                toKoreaAttendanceStatus(attendance.getAttendanceStatus()));
    }

    private String toAbsentFullFormat(LocalDate date) {
        return String.format("%s (결석)", date.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--")));
    }

    private String toKoreaAttendanceStatus(AttendanceStatus attendanceStatus) {
        return switch (attendanceStatus) {
            case ATTENDANCE -> "출석";
            case LATE -> "지각";
            case ABSENT -> "결석";
        };
    }

    private String toKoreaWarningLevel(WarningLevel warningLevel) {
        return switch (warningLevel) {
            case NONE -> "";
            case WARNING -> "경고";
            case INTERVIEW -> "면담";
            case WEEDING -> "제적";
        };
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }
}
