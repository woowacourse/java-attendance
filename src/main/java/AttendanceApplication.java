import presentation.AttendanceController;
import presentation.view.AttendanceFileInputView;
import service.CrewService;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceFileInputView fileInputView = new AttendanceFileInputView();
        CrewService attendanceService = new CrewService();
        AttendanceController attendanceController = new AttendanceController(fileInputView, attendanceService);
        attendanceController.run();
    }
}
