package controller;

import attendance.AttendanceHistory;
import view.InputView;

public class AttendanceController {

    private final InputView inputView = InputView.create();
    private final AttendanceHistory attendanceHistory = AttendanceHistory.create();
    public void start() {
        String inputNickname = inputView.inputNickname();
        attendanceHistory.isValidCrew(inputNickname);
    }
}
