package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceRequest;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<AttendanceRequest> savedAttendances = new AttendanceReader().loadAttendanceLinesFromAttendanceFile();
        AttendanceController attendanceController = new AttendanceController(new AttendanceManager(savedAttendances));
        attendanceController.start();
    }
}
