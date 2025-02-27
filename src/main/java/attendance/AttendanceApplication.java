package attendance;

import attendance.controller.AttendanceController;

public class AttendanceApplication {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController();
        controller.run();
    }
}
