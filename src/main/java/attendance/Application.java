package attendance;

import attendance.controller.AttendanceController;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
                LocalDate.of(2024, 12, 16)
        );
        controller.run();
    }
}
