package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Warning;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.UpdateAfterAttendanceResponse;
import attendance.dto.UpdateBeforeAttendanceResponse;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(AttendanceResultResponse response) {
        LocalDateTime dateTime = response.dateTime();

        System.out.printf("%d월 %2d일 %s %02d:%02d (%s)\n", dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                response.status());
    }

    public void printUpdateAttendance(UpdateBeforeAttendanceResponse beforeResponse, UpdateAfterAttendanceResponse afterResponse) {
        LocalDateTime beforeDateTime = beforeResponse.dateTime();
        LocalDateTime afterDateTime = afterResponse.dateTime();
        System.out.printf("%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                beforeDateTime.getMonthValue(),
                beforeDateTime.getDayOfMonth(),
                beforeDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                beforeResponse.status(),
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterResponse.status()
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
            printAttendanceResult(AttendanceResultResponse.of(attendance));
        }
        System.out.printf("출석 : %d회\n", crew.countAttend());
        System.out.printf("지각 : %d회\n", crew.countLate());
        System.out.printf("결석 : %d회\n", crew.countAbsence());
    }

    public void printWarning(Warning warning) {
        System.out.printf("%s 대상자입니다.\n", warning.getMessage());
    }

    public void printWarningCrews(List<Crew> crews) {
        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : crews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    crew.getNickname(),
                    crew.countAbsence(),
                    crew.countLate(),
                    crew.checkWarning().getMessage()
            );
        }
    }

    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
