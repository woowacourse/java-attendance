import controller.Controller;
import domain.AttendanceBook;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new InputView(), new AttendanceBook());

        controller.runSystem();
    }
}
