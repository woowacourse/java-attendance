import controller.AttendanceController;
import domain.AttendanceBook;
import domain.timeprovider.NowTimeProvider;
import domain.timeprovider.TimeProvider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Clock;
import util.filereader.CsvFileReader;
import util.filereader.FileReader;
import util.inputreader.ConsoleInputReader;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private static final String CSV_FILE_PATH = "src/main/resources/attendances.csv";

    public AttendanceController attendanceController() {
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
        return new CsvFileReader(CSV_FILE_PATH);
    }

    private TimeProvider timeProvider() {
        return new NowTimeProvider(Clock.systemDefaultZone());
    }
}
