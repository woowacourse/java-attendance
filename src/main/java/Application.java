import controller.AttendanceController;
import domain.DateProvider;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class Application {

    private static final int YEAR = 2024;
    private static final int MONTH = 12;
    private static final int TODAY = LocalDate.now().getDayOfMonth();
    private static final String FILEPATH = "src/main/resources/attendances.csv";

    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DateProvider dateProvider = DateProvider.of(YEAR, MONTH, TODAY);

        AttendanceController attendanceController = new AttendanceController(
                inputView,
                outputView,
                dateProvider,
                FILEPATH);

        attendanceController.run();

    }
}
