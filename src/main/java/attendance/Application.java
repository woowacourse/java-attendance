package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new InputView());
        attendanceController.run();
    }
}
