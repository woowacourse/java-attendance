import controller.AttendanceController;
import view.InputValidator;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputValidator inputValidator = new InputValidator();
        InputView inputView = new InputView(inputValidator);
        OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(inputView, outputView);

        attendanceController.run();
    }
}
