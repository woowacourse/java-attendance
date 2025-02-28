import controller.AttendanceController;
import service.CrewRegistrationService;
import view.input.InputView;
import view.output.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceController attendanceController =
                new AttendanceController(inputView, outputView, new CrewRegistrationService());
        attendanceController.start();
    }
}