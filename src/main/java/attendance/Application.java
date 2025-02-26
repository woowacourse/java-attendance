package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                new InputView(),
                new OutputView()
        );
        attendanceController.run();
    }
}
