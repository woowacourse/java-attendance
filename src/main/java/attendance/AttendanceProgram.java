package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceProgram {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2024, 12, 13);
        AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.run(today);
    }
}
