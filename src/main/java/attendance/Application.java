package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.ResultView;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new InputView(), new ResultView());
        attendanceController.run();
    }
}
