package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;

public class AttendanceProgram {

    public static void main(String[] args) {
        new AttendanceController(new InputView()).run();
    }
}
