import presentation.AttendanceController;
import view.AttendanceFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                new AttendanceFileReader(),
                new InputView(),
                new OutputView());
        attendanceController.run();
    }
}
