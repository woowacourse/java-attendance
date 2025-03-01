package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        final AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.start();
    }
}
