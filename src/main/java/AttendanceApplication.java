import controller.AttendanceController;
import domain.DateProvider;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args, DateProvider dateProvider) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final AttendanceController attendanceController = new AttendanceController(
                dateProvider, inputView, outputView
        );

        attendanceController.run();
    }
}
