package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENT;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;

import attendance.domain.Attendance;
import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceStatus;
import attendance.domain.Holiday;
import attendance.domain.WarningLevel;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
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

    private void printAttendanceStatus(AttendanceResult attendanceResult) {
        Map<AttendanceStatus, Integer> attendanceStatus = attendanceResult.getAttendanceStatus();
        System.out.printf("%s: %d회\n",
                toKoreaAttendanceStatus(ATTENDANCE),
                attendanceStatus.getOrDefault(ATTENDANCE, 0));
        System.out.printf("%s: %d회\n", toKoreaAttendanceStatus(LATE), attendanceStatus.getOrDefault(LATE, 0));
        System.out.printf("%s: %d회\n", toKoreaAttendanceStatus(ABSENT), attendanceStatus.getOrDefault(ABSENT, 0));
    }

    private boolean isNotPrintDate(LocalDate currentDate) {
        return currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(MonthDay.from(currentDate));
    }

    private void printAllAttendances(LocalDate endDate, Map<LocalDate, Attendance> attendanceMap) {
        LocalDate currentDate = LocalDate.of(2024, 12, 2);
        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            if (isNotPrintDate(currentDate)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }
            if (attendanceMap.containsKey(currentDate)) {
                System.out.println(toAttendanceFullFormat(attendanceMap.get(currentDate)));
                currentDate = currentDate.plusDays(1);
                continue;
            }
            System.out.println(toAbsentFullFormat(currentDate));
            currentDate = currentDate.plusDays(1);
        }
    }

    private void printWarningLevel(WarningLevel warningLevel) {
        if (warningLevel == WarningLevel.NONE) {
            return;
        }
        System.out.printf("%s 대상자입니다.", toKoreaWarningLevel(warningLevel));
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
}
