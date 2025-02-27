package attendance;

import attendance.controller.AttendanceControllerImpl;

public class AttendanceApplication {

    public static void main(String[] args) {
        AttendanceControllerImpl controller = new AttendanceControllerImpl();
        controller.run();
    }
}
