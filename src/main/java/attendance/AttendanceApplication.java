package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceApplication {

    public static void main(String[] args) {
        LocalDate baseDate = LocalDate.of(2024, 12, 13);
        AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.start(baseDate);
    }
}
