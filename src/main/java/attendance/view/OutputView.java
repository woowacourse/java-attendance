package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.dto.AttendanceHistoryResponse;
import attendance.dto.AttendanceResponse;
import attendance.dto.ModifyAttendanceResponse;
import attendance.dto.RiskCrewsResponse;
import attendance.util.DateTimeUtil;

public class OutputView {

    private OutputView() {
    }

    public static void attendanceResponse(AttendanceResponse response) {
        System.out.printf(
            "%n" + response.dateTime().format(DateTimeUtil.DATE_TIME_FORMATTER)
                + " (" + response.status().getName() + ")%n%n");
    }

    public static void modifyAttendanceResponse(ModifyAttendanceResponse response) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n%n",
            response.date().format(DateTimeUtil.DATE_FORMATTER),
            response.before().time().format(DateTimeUtil.TIME_FORMATTER),
            response.before().status().getName(),
            response.after().time().format(DateTimeUtil.TIME_FORMATTER),
            response.after().status().getName()
        );
    }

    // TODO : 삼항연산자 제거
    // TODO : forEach 제거
    public static void attendanceHistoryResponse(AttendanceHistoryResponse response) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", response.name());
        response.histories().forEach(history -> {
            if (history.status() == AttendanceStatus.DAY_OFF) {
                return;
            }

            System.out.printf("%s %s (%s)%n",
                history.date().format(DateTimeUtil.DATE_FORMATTER),
                history.time() == null ? "--:--" : history.time().format(DateTimeUtil.TIME_FORMATTER),
                history.status().getName());
        });
        System.out.println();
        System.out.printf("""
                출석: %d회
                지각: %d회
                결석: %d회
                """,
            response.statistics().get(AttendanceStatus.ATTENDANCE),
            response.statistics().get(AttendanceStatus.LATENESS),
            response.statistics().get(AttendanceStatus.ABSENCE));
        if (response.risk() != null) {
            System.out.printf("%n%s 대상자입니다.%n%n", response.risk().getName());
        }
    }

    public static void riskCrewsResponse(RiskCrewsResponse response) {
        System.out.printf("%n제적 위험자 조회 결과%n");
        response.crews().stream()
            .sorted()
            .forEach(crew ->
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                crew.name(),
                crew.statistics().get(AttendanceStatus.ABSENCE),
                crew.statistics().get(AttendanceStatus.LATENESS),
                crew.risk().getName()
            ));
        System.out.println();
    }

    public static void exception(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력하세요.");
    }
}
