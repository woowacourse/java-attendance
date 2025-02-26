package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                new InputView(),
                new OutputView(),
                LocalDate.of(2024,12,13)
        );
        attendanceController.run();
    }
}
