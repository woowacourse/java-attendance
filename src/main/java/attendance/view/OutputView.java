package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Warning;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
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
    }

    public void printUpdateAttendance(LocalDateTime beforeTime, AttendanceStatus beforeStatus, Attendance after) {
        LocalDateTime afterDateTime = after.getDateTime();
        System.out.printf("%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                beforeTime.getMonthValue(),
                beforeTime.getDayOfMonth(),
                beforeTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                beforeTime.getHour(),
                beforeTime.getMinute(),
                beforeStatus.getMessage(),
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                after.getStatus().getMessage()
        );
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

    public void printWarning(Warning warning) {
        System.out.printf("%s 대상자입니다.\n", warning.getMessage());
    }

    public void printWarningCrews(List<Crew> crews) {
        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : crews) {
            Map<AttendanceStatus, Integer> statusCount = crew.getStatusCount();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    crew.getNickname(),
                    statusCount.getOrDefault(AttendanceStatus.ABSENCE, 0)
                            + statusCount.getOrDefault(AttendanceStatus.LATE_ABSENCE, 0),
                    statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                    crew.checkWarning().getMessage()
            );
        }
    }
}
