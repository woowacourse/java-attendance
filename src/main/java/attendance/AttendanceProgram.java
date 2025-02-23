package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;

public class AttendanceProgram {

    public static void main(String[] args) {
        initializeAttendanceController().startAttendanceSystem(initializeBaseTime());
    }

    private static AttendanceController initializeAttendanceController() {
        return new AttendanceController(new InputView(), new OutputView());
    }

    private static LocalDateTime initializeBaseTime() {
        return LocalDateTime.now();
    }
}
