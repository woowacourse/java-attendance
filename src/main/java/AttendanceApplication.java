import controller.AttendanceController;
import domain.attendance.AttendanceHistories;
import domain.crew.Crews;
import service.AttendanceService;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceService(new Crews(), new AttendanceHistories());
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        attendanceController.run();
    }
}

