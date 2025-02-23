import presentation.AttendanceController;
import presentation.view.FileInputView;
import service.CrewService;

public class AttendanceApplication {
    public static void main(String[] args) {
        FileInputView fileInputView = new FileInputView();
        CrewService attendanceService = new CrewService();
        AttendanceController attendanceController = new AttendanceController(fileInputView, attendanceService);
        attendanceController.run();
    }
}
