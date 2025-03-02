package view;

import domain.AttendanceDate;
import domain.AttendanceStatus;
import global.utils.DateTimeUtil;
import view.utils.ViewUtil;

import static global.utils.DateTimeUtil.*;

public class OutputView {
    public void printErrorMessage(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    public void printSelectMenuMessage() {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", convertDateWithDayOfWeekFormat(DateTimeUtil.getFixedRunningDate()));
    }

    public void printEditResultMessage(AttendanceDate originalAttendanceDate, AttendanceDate editedAttendanceDate) {
        System.out.printf("%s %s (%s) -> %s (%s)\n", convertDateWithDayOfWeekFormat(originalAttendanceDate.getDate()), convertTimeFormat(originalAttendanceDate.getTime()), ViewUtil.getAttendanceStatusMessage(originalAttendanceDate.getStatus())
                , convertTimeFormat(editedAttendanceDate.getTime()), ViewUtil.getAttendanceStatusMessage(editedAttendanceDate.getStatus()));
    }

    public void printAttendResultMessage(AttendanceDate attendanceDate) {
        if (attendanceDate.getStatus().equals(AttendanceStatus.NONE)) {
            System.out.printf("%s %s (%s)\n", convertDateWithDayOfWeekFormat(attendanceDate.getDate()), ViewUtil.getEmptyStatusMessage(), ViewUtil.getAttendanceStatusMessage(attendanceDate.getStatus()));
            return;
        }
        System.out.printf("%s %s (%s)\n", convertDateWithDayOfWeekFormat(attendanceDate.getDate()), convertTimeFormat(attendanceDate.getTime()), ViewUtil.getAttendanceStatusMessage(attendanceDate.getStatus()));
    }

    public void printAttendanceResult(String name) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", name);
    }

    public void printRiskStatusResult() {
        System.out.println("제적 위험자 조회 결과");

        System.out.printf("- %s: 결석 %d회, 지각 %d회 %s\n", "이름", 1, 1, "(상태)");
    }
}
