package view;

import domain.AttendanceDate;
import domain.AttendanceStatus;
import domain.RiskStatus;
import domain.RiskStatusResult;
import global.utils.DateTimeUtil;

import java.util.List;

import static view.utils.ViewUtil.*;

import static global.utils.DateTimeUtil.*;

public class OutputView {
    public void printErrorMessage(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    public void printSelectMenuMessage() {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", convertDateWithDayOfWeekFormat(DateTimeUtil.getFixedRunningDate()));
    }

    public void printEditResultMessage(AttendanceDate originalAttendanceDate, AttendanceDate editedAttendanceDate) {
        System.out.printf("%s %s (%s) -> %s (%s)\n", convertDateWithDayOfWeekFormat(originalAttendanceDate.getDate()), convertTimeFormat(originalAttendanceDate.getTime()), getAttendanceStatusMessage(originalAttendanceDate.getStatus())
                , convertTimeFormat(editedAttendanceDate.getTime()), getAttendanceStatusMessage(editedAttendanceDate.getStatus()));
    }

    public void printAttendResultMessage(AttendanceDate attendanceDate) {
        if (attendanceDate.getStatus().equals(AttendanceStatus.NONE)) {
            System.out.printf("%s %s (%s)\n", convertDateWithDayOfWeekFormat(attendanceDate.getDate()), getEmptyStatusMessage(), getAttendanceStatusMessage(attendanceDate.getStatus()));
            return;
        }
        System.out.printf("%s %s (%s)\n", convertDateWithDayOfWeekFormat(attendanceDate.getDate()), convertTimeFormat(attendanceDate.getTime()), getAttendanceStatusMessage(attendanceDate.getStatus()));
    }

    public void printAttendanceResult(String name) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", name);
    }

    public void printAttendanceCountResult(RiskStatusResult riskStatusResult) {
        System.out.printf("출석 : %d회\n", riskStatusResult.attendanceCount());
        System.out.printf("지각 : %d회\n", riskStatusResult.tardyCount());
        System.out.printf("결석 : %d회\n\n", riskStatusResult.absenceCount());

        if (!riskStatusResult.riskStatus().equals(RiskStatus.NONE)) {
            System.out.printf("%s 대상자입니다.", getRiskStatusMessage(riskStatusResult.riskStatus()));
        }
    }

    public void printRiskStatusResult(List<RiskStatusResult> riskStatusResults) {
        System.out.println("제적 위험자 조회 결과");
        riskStatusResults.forEach(riskStatusResult -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", riskStatusResult.name(), riskStatusResult.absenceCount(), riskStatusResult.tardyCount(), getRiskStatusMessage(riskStatusResult.riskStatus()));
        });
        System.out.println();

    }
}
