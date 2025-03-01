package attendance;

import attendance.controller.AttendanceController;
import attendance.view.*;

public class Application {

    public static void main(String[] args) {
        GeneralView generalView = new GeneralView();
        AttendanceConfirmView attendanceConfirmView = new AttendanceConfirmView();
        AttendanceModifyView attendanceModifyView = new AttendanceModifyView();
        CrewAttendanceCheckView crewAttendanceCheckView = new CrewAttendanceCheckView();
        CheckAllExpulsionCrewView checkAllExpulsionCrewView = new CheckAllExpulsionCrewView();
        AttendanceController attendanceController = new AttendanceController(
                generalView,
                attendanceConfirmView,
                attendanceModifyView,
                crewAttendanceCheckView,
                checkAllExpulsionCrewView);
        attendanceController.run();
    }
}
