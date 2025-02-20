import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {

    public static void main(String[] args) {
        final AttendanceController controller = new AttendanceController(new InputView(), new OutputView());
        controller.run();
    }
}
