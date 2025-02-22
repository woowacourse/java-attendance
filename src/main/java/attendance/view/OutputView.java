package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.domain.Warning;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.CrewAttendanceResponse;
import attendance.dto.UpdateAfterAttendanceResponse;
import attendance.dto.UpdateBeforeAttendanceResponse;
import attendance.dto.WarningCrewResponse;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(AttendanceResultResponse response) {
        LocalDateTime dateTime = response.dateTime();
        AttendanceStatus status = response.status();
        if (status == AttendanceStatus.ABSENCE) {
            System.out.printf("%d월 %2d일 %s --:-- (%s)\n", dateTime.getMonthValue(),
                    dateTime.getDayOfMonth(),
                    dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                    status.getMessage());
            return;
        }
        System.out.printf("%d월 %2d일 %s %02d:%02d (%s)\n", dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                status.getMessage());
    }

    public void printUpdateAttendance(UpdateBeforeAttendanceResponse beforeResponse,
                                      UpdateAfterAttendanceResponse afterResponse) {
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

    public void printAttendanceByCrew(CrewAttendanceResponse response) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", response.nickname());
        for (AttendanceResultResponse attendance : response.attendances()) {
            printAttendanceResult(attendance);
        }
        System.out.printf("출석 : %d회\n", response.attendCount());
        System.out.printf("지각 : %d회\n", response.lateCount());
        System.out.printf("결석 : %d회\n", response.absenceCount());
    }

    public void printWarning(Warning warning) {
        System.out.printf("%s 대상자입니다.\n", warning.getMessage());
    }

    public void printWarningCrews(List<WarningCrewResponse> responses) {
        System.out.println("제적 위험자 조회 결과");
        responses.stream()
                .sorted(Comparator.comparing(WarningCrewResponse::absenceCount)
                        .thenComparing(WarningCrewResponse::lateCount)
                        .thenComparing(WarningCrewResponse::nickname))
                .forEach(response -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                        response.nickname(),
                        response.absenceCount(),
                        response.lateCount(),
                        response.warning().getMessage()
                ));
    }

    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
