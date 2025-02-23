package attendance.view;

import attendance.model.Attendance;
import attendance.model.AttendanceResult;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String ERROR_PREFIX = "[ERROR] ";

    public void printCheckAttendance(Attendance attendance) {
        System.out.printf("%s (%s)%n",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                attendance.getAttendanceType()
        );
    }

    public void printModifiedAttendance(Attendance beforeAttendance, Attendance afterAttendance) {
        if (beforeAttendance.getAttendanceType() != AttendanceType.ABSENCE) {
            System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                    beforeAttendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                    displayAttendanceType(beforeAttendance.getAttendanceType()),
                    afterAttendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    displayAttendanceType(afterAttendance.getAttendanceType())
            );
            return;
        }
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                afterAttendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--")),
                displayAttendanceType(beforeAttendance.getAttendanceType()),
                afterAttendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                displayAttendanceType(afterAttendance.getAttendanceType())
        );
    }

    public void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public void printDateTimeErrorMessage() {
        System.out.println(ERROR_PREFIX + "HH:mm (24시간) 형식만 사용할 수 있습니다.");
    }

    public void printAttendanceResult(AttendanceResult attendanceResult) {
        printMonthlyAttendance(attendanceResult.getCrew().getNickname(), attendanceResult.getAttendances());
        printCountOfAttendanceType(attendanceResult.getAttendanceTypes());
        printWarningLevel(attendanceResult.getAttendanceWarningLevel());
    }

    public void printEmergencyCrews(List<AttendanceResult> attendanceResults) {
        Collections.sort(attendanceResults);
        System.out.println("제적 위험자 조회 결과");
        attendanceResults.stream()
                .filter(attendanceResult ->
                        attendanceResult.getAttendanceWarningLevel() != AttendanceWarningLevel.CLEAN)
                .forEach(attendanceResult -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                        attendanceResult.getCrew().getNickname(),
                        attendanceResult.getAttendanceTypes().getOrDefault(AttendanceType.ABSENCE, 0),
                        attendanceResult.getAttendanceTypes().getOrDefault(AttendanceType.LATE, 0),
                        displayAttendanceWarningLevel(attendanceResult.getAttendanceWarningLevel())));
    }

    private void printMonthlyAttendance(String nickname, List<Attendance> attendances) {
        System.out.printf("이번달 %s의 출석 기록입니다.%n%n", nickname);
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceTime() == null) {
                System.out.printf("%s --:-- (%s)%n",
                        attendance.getAttendanceDate().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                        displayAttendanceType(attendance.getAttendanceType()));
                continue;
            }
            System.out.printf("%s (%s)%n",
                    attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                    displayAttendanceType(attendance.getAttendanceType()));
        }
    }

    private void printCountOfAttendanceType(Map<AttendanceType, Integer> attendanceTypes) {
        int okCount = attendanceTypes.getOrDefault(AttendanceType.OK, 0);
        int lateCount = attendanceTypes.getOrDefault(AttendanceType.LATE, 0);
        int absenceCount = attendanceTypes.getOrDefault(AttendanceType.ABSENCE, 0);
        System.out.printf("%n출석: %d%n지각: %d%n결석: %d%n", okCount, lateCount, absenceCount);
    }

    private void printWarningLevel(AttendanceWarningLevel level) {
        if (level != AttendanceWarningLevel.CLEAN) {
            System.out.printf("%n%s 대상자입니다.", displayAttendanceWarningLevel(level));
        }
    }

    private String displayAttendanceType(AttendanceType type) {
        if (type == AttendanceType.OK) {
            return "출석";
        }
        if (type == AttendanceType.LATE) {
            return "지각";
        }
        return "결석";
    }

    private String displayAttendanceWarningLevel(AttendanceWarningLevel level) {
        if (level == AttendanceWarningLevel.WARNING) {
            return "경고";
        }
        if (level == AttendanceWarningLevel.MEETING) {
            return "면담";
        }
        return "제적";
    }
}
