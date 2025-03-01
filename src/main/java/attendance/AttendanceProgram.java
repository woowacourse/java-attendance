package attendance;

import attendance.controller.AttendanceController;
import attendance.initialize.AttendanceBookInitializer;
import attendance.io.file.AttendanceInfoLinesReader;
import attendance.io.view.InputView;
import attendance.io.view.OutputView;
import java.time.LocalDate;

public class AttendanceProgram {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2024, 12, 13);
        AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.run(today, new AttendanceBookInitializer(new AttendanceInfoLinesReader()));
    }
}
