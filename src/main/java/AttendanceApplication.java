import presentation.AttendanceController;
import presentation.view.FileInputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        FileInputView fileInputView = new FileInputView();
        AttendanceController attendanceController = new AttendanceController(fileInputView);
        attendanceController.run();
    }
}
