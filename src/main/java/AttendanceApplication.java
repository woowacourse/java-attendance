import presentation.AttendanceController;
import presentation.view.FileInputView;
import service.AttendanceService;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceService();
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        attendanceController.run();
    }
}
