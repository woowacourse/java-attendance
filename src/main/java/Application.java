import controller.AttendanceController;
import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();

        AttendanceBook attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));

        Clock systemClock = Clock.systemDefaultZone();

        // 수동 테스트용 Clock 객체
        //systemClock = Clock.fixed(Instant.parse("2025-03-05T10:00:00Z"), ZoneId.of("Asia/Seoul"));

        AttendanceController controller = new AttendanceController(attendanceBook, inputView, outputView, systemClock);
        controller.run();
    }
}
