package attendance;

import attendance.controller.AttendanceController;
import attendance.view.AttendanceConfirmView;

public class Application {

    public static void main(String[] args) {
        AttendanceConfirmView attendanceConfirmView = new AttendanceConfirmView();
        AttendanceController attendanceController = new AttendanceController(attendanceConfirmView);
        attendanceController.run();
    }
}
