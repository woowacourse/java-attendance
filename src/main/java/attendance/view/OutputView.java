package attendance.view;

import attendance.controller.AttendanceController.CrewAttendanceSummary;
import attendance.model.Attendance;
import attendance.model.AttendanceTimeline;
import attendance.model.AttendanceTimeline.AttendanceLog;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputView {

    public void printCheckAttendance(LocalDateTime dateTime, AttendanceType type) {
        System.out.printf("%s (%s)%n",
                dateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                displayAttendanceType(type)
        );
    }

    public void printModifiedAttendance(Attendance beforeAttendance, Attendance afterAttendance, AttendanceType beforeType, AttendanceType afterType) {
        if (beforeAttendance != null) {
            System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                    beforeAttendance.getDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                    displayAttendanceType(beforeType),
                    afterAttendance.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    displayAttendanceType(afterType)
            );
            return;
        }
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                afterAttendance.getDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--")),
                displayAttendanceType(null),
                afterAttendance.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                displayAttendanceType(afterType)
        );
    }

    public void printAttendanceTimelineInMonth(String nickname, AttendanceTimeline attendanceTimeline) {
        System.out.printf("이번달 %s의 출석 기록입니다.%n%n", nickname);
        for (AttendanceLog attendanceLog : attendanceTimeline.attendanceLogs()) {
            if (attendanceLog.time() == null) {
                System.out.printf("%s --:-- (%s)%n",
                        attendanceLog.date().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                        displayAttendanceType(attendanceLog.attendanceType()));
                continue;
            }
            System.out.printf("%s %s (%s)%n",
                    attendanceLog.date().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
                    attendanceLog.time().format(DateTimeFormatter.ofPattern("HH:mm")),
                    displayAttendanceType(attendanceLog.attendanceType()));
        }
    }

    public void printCountOfAttendanceType(int okCount, int lateCount, int absenceCount) {
        System.out.printf("%n출석: %d%n지각: %d%n결석: %d%n", okCount, lateCount, absenceCount);
    }

    public void printWarningLevel(AttendanceWarningLevel level) {
        if (level != AttendanceWarningLevel.CLEAN) {
            System.out.printf("%n%s 대상자입니다.", displayAttendanceWarningLevel(level));
        }
    }

    public void printEmergencyCrews(List<CrewAttendanceSummary> sortedList) {
        System.out.println("제적 위험자 조회 결과");
        sortedList.stream()
                .filter(summary -> summary.level() != AttendanceWarningLevel.CLEAN)
                .forEach(summary -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                        summary.crew().getNickname(),
                        summary.absenceCount(),
                        summary.lateCount(),
                        displayAttendanceWarningLevel(summary.level())));
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
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
