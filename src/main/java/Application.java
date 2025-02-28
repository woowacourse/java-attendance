import controller.Controller;
import service.CrewRegistrationService;
import view.input.InputView;
import view.output.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO : 작성할 것
        Controller controller = new Controller(new InputView(), new OutputView(), new CrewRegistrationService());
        controller.start();
    }
}