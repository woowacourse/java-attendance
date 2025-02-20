import presentation.AttendanceController;
import presentation.view.FileInputView;
import service.AttendanceService;

public class AttendanceApplication {
    public static void main(String[] args) {
        FileInputView fileInputView = new FileInputView();
        AttendanceService attendanceService = new AttendanceService();
        AttendanceController attendanceController = new AttendanceController(fileInputView, attendanceService);
        attendanceController.run();
    }
}
