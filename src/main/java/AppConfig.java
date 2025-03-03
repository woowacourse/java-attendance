import controller.AttendanceController;
import domain.AttendanceBook;
import domain.NowTimeProvider;
import domain.TimeProvider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Clock;
import util.ConsoleInputReader;
import util.CsvFileReader;
import util.FileReader;
import view.InputView;
import view.OutputView;

public class AppConfig {
    public AttendanceController controller() {
        return new AttendanceController(attendanceBook(), inputView(), outputView(),
                fileReader(), timeProvider());
    }

    private ConsoleInputReader inputReader() {
        return new ConsoleInputReader(new BufferedReader(new InputStreamReader(System.in)));
    }

    private InputView inputView() {
        return new InputView(inputReader());
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private AttendanceBook attendanceBook() {
        return new AttendanceBook();
    }

    private FileReader fileReader() {
        return new CsvFileReader("src/main/resources/attendances.csv");
    }

    private TimeProvider timeProvider() {
        return new NowTimeProvider(Clock.systemDefaultZone());
    }
}
