package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.validation.AttendanceInputValidator;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<String> savedAttendances = new AttendanceReader().loadAttendanceLinesFromAttendanceFile();
        AttendanceController attendanceController = new AttendanceController(
                new AttendanceManager(savedAttendances), new AttendanceInputValidator());
        attendanceController.start();
    }
}
