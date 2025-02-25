package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceProgram {

    public static void main(String[] args) {
        new AttendanceController(new InputView(), new OutputView()).run();
    }
}
