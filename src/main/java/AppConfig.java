import controller.AttendanceController;
import service.CrewRegistrationService;
import service.FunctionService;
import view.input.InputView;
import view.output.OutputView;

public class AppConfig {
    private InputView inputView;
    private OutputView outputView;

    public AttendanceController controller() {
        return new AttendanceController(inputView(), outputView(), registrationService(), functionService());
    }

    private InputView inputView() {
        if (this.inputView == null) {
            this.inputView = new InputView();
        }
        return this.inputView;
    }

    private OutputView outputView() {
        if (this.outputView == null) {
            this.outputView = new OutputView();
        }
        return this.outputView;
    }

    private CrewRegistrationService registrationService() {
        return new CrewRegistrationService();
    }

    private FunctionService functionService() {
        return new FunctionService(outputView(), inputView());
    }
}