import controller.MainController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        MainController controller = new MainController(new InputView(), new OutputView());
        controller.run();
    }
}
