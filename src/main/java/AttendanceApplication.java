import controller.AttendanceController;
import domain.CrewAttendanceRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import util.DataInitializer;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        LocalDateTime today = parseDateTimeFromArgs(args);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DataInitializer dataInitializer = new DataInitializer();
        dataInitializer.initialize(today, "src/main/resources/attendances.csv");
        AttendanceController controller = new AttendanceController(inputView, outputView,
                CrewAttendanceRepository.getInstance());
        controller.run(today);
    }

    private static LocalDateTime parseDateTimeFromArgs(String[] args) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(args[0], formatter);
        } catch (Exception e) {
            return LocalDateTime.of(2024, 12, 13, 0, 0);
        }
    }
}
