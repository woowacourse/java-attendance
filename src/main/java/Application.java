import controller.MainController;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MainController mainController = new MainController(new InputView(), new OutputView());
        mainController.run();
    }
}
