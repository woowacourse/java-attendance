import controller.AttendanceController;
import service.CrewRegistrationService;
import view.input.InputView;
import view.output.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO : 작성할 것
        AttendanceController attendanceController =
                new AttendanceController(new InputView(), new OutputView(), new CrewRegistrationService());
        attendanceController.start();
    }
}