package attendance;

import attendance.controller.AttendanceController;
import attendance.infrastructure.Initializer;

public class Application {

    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new Initializer());
        attendanceController.run();
    }
}
