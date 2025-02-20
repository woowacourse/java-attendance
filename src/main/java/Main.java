import controller.AttendanceController;
import view.FileInputView;

public class Main {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new FileInputView());
        attendanceController.run();
    }
}
