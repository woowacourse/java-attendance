import controller.AttendanceController;
import view.input.InputView;
import view.output.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final AttendanceController attendanceController = new AttendanceController(
                inputView,
                outputView
        );

        attendanceController.run();
    }
}
