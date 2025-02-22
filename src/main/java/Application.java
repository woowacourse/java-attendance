import controller.AttendanceController;
import java.io.IOException;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(inputView, outputView);
        attendanceController.run();
    }
}
