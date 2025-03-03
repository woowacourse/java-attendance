package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {

    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);

        attendanceController.run();
    }
}
