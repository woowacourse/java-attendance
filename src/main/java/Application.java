import controller.Controller;
import service.DateValidator;
import view.InputValidator;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputValidator inputValidator = new InputValidator();
        InputView inputView = new InputView(inputValidator);
        OutputView outputView = new OutputView();
        DateValidator dateValidator = new DateValidator();
        Controller controller = new Controller(dateValidator, inputView, outputView);

        controller.run();
    }
}
