import controller.AttendanceController;
import domain.AttendanceHistoryLoader;
import domain.Crews;
import java.io.FileNotFoundException;
import java.io.FileReader;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        Crews crews = attendanceHistoryLoader.loadCrews(new FileReader("src/main/resources/attendances.csv"));
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(crews, inputView, outputView);
        attendanceController.run();
    }
}
