package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();
        AttendanceStatus status = attendance.getStatus();

        System.out.printf("%d월 %2d일 %s %02d:%02d (%s)\n", dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                status.getMessage());
        //12월 05일 화요일 09:59 (출석)
    }

    public void printAttendanceByCrew(Crew crew) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crew.getNickname());
        for (Attendance attendance : crew.getAttendances()) {
            LocalDateTime dateTime = attendance.getDateTime();
            AttendanceStatus status = attendance.getStatus();
            if (status == AttendanceStatus.ABSENCE) {
                System.out.printf("%d월 %2d일 %s --:-- (%s)\n", dateTime.getMonthValue(),
                        dateTime.getDayOfMonth(),
                        dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                        status.getMessage());
                continue;
            }
            printAttendanceResult(attendance);
        }

        Map<AttendanceStatus, Integer> statusCount = crew.getStatusCount();
        System.out.printf("출석 : %d회\n", statusCount.getOrDefault(AttendanceStatus.ATTEND, 0));
        System.out.printf("지각 : %d회\n", statusCount.getOrDefault(AttendanceStatus.LATE, 0));
        System.out.printf("결석 : %d회\n", (statusCount.getOrDefault(AttendanceStatus.ABSENCE, 0)) + statusCount.getOrDefault(AttendanceStatus.LATE_ABSENCE, 0));
    }
}
