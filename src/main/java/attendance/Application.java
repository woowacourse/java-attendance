package attendance;

import attendance.controller.AttendanceController;
import attendance.view.AttendanceConfirmView;
import attendance.view.AttendanceModifyView;

public class Application {

    public static void main(String[] args) {
        AttendanceConfirmView attendanceConfirmView = new AttendanceConfirmView();
        AttendanceModifyView attendanceModifyView = new AttendanceModifyView();
        AttendanceController attendanceController = new AttendanceController(attendanceConfirmView, attendanceModifyView);
        attendanceController.run();
    }
}
