import controller.AttendanceController;
import domain.DateProvider;
import domain.SystemDateProvider;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    // 기존 main 메서드
    public static void main(String[] args) {
        DateProvider dateProvider = new SystemDateProvider();
        main(args, dateProvider);
    }

    public static void main(String[] args, DateProvider dateProvider) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final AttendanceController attendanceController = new AttendanceController(
                dateProvider, inputView, outputView
        );

        attendanceController.run();
    }
}
