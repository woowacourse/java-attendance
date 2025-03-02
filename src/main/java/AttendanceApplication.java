import controller.AttendanceController;
import domain.SystemDateProvider;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final SystemDateProvider systemDateProvider = new SystemDateProvider();

        final AttendanceController attendanceController = new AttendanceController(
                systemDateProvider, inputView, outputView
        );

        attendanceController.run();
    }
}
