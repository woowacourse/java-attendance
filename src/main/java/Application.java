import controller.Controller;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new InputView());

        controller.runSystem();
    }
}
