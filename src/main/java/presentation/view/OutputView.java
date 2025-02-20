package presentation.view;

import domain.attendance.AttendanceState;

public class OutputView {
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    //12월 13일 금요일 09:59 (출석)
    public static void printAttend(String attendanceTime, AttendanceState attendanceState) {
        System.out.println(attendanceTime + "(" + attendanceState.getState() + ")");
    }
}
