import controller.AttendanceController;
import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import java.io.FileReader;
import java.io.IOException;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();

        AttendanceBook attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));

        AttendanceController controller = new AttendanceController(attendanceBook, inputView, outputView);
        controller.run();
    }
}
