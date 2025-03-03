package attendance.controller;

import attendance.view.InputView;

public class AttendanceController {
    private final InputView inputView;

    public AttendanceController(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        String inputOption = inputView.readOption();
        if (inputOption.equals("3")) {
            showCrewAttendance();
        }
    }

    private void showCrewAttendance() {
        String nickname = inputView.readNickname();
    }
}
