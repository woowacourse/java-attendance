import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);
        attendanceController.start();
    }
}
