package attendance;

import attendance.controller.AttendanceController;
import attendance.view.AttendanceConfirmView;
import attendance.view.AttendanceModifyView;
import attendance.view.CrewAttendanceCheckView;

public class Application {

    public static void main(String[] args) {
        AttendanceConfirmView attendanceConfirmView = new AttendanceConfirmView();
        AttendanceModifyView attendanceModifyView = new AttendanceModifyView();
        CrewAttendanceCheckView crewAttendanceCheckView = new CrewAttendanceCheckView();
        AttendanceController attendanceController = new AttendanceController(
                attendanceConfirmView,
                attendanceModifyView,
                crewAttendanceCheckView);
        attendanceController.run();
    }
}
