package attendance.controller;

import attendance.configuration.ApplicationConfiguration;
import attendance.domain.AttendanceSystem;
import attendance.view.InputView;

public class AttendanceController {

    private final AttendanceSystem attendanceSystem;
    private final InputView inputView;

    public AttendanceController(ApplicationConfiguration configuration) {
        attendanceSystem = configuration.getAttendanceSystem();
        inputView = configuration.getInputView();
    }

    public void run() {
        while (true) {
            MenuCommand menuCommand = inputView.readMenuCommand();
            if (menuCommand == MenuCommand.FIRST) {

            }
            if (menuCommand == MenuCommand.SECOND) {

            }
            if (menuCommand == MenuCommand.THIRD) {

            }
            if (menuCommand == MenuCommand.FOURTH) {

            }
            if (menuCommand == MenuCommand.QUIT) {
                return;
            }
        }
    }
}
