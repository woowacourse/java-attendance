import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.run();
    }
}
