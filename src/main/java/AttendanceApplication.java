import controller.AttendanceController;
import domain.CrewAttendanceRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import util.DataInitializer;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        LocalDate currentDate = parseDate(args);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DataInitializer dataInitializer = new DataInitializer();
        dataInitializer.initialize(currentDate, "src/main/resources/attendances.csv");
        AttendanceController controller = new AttendanceController(inputView, outputView,
                CrewAttendanceRepository.getInstance());
        
        controller.run(currentDate);
    }

    private static LocalDate parseDate(String[] args) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(args[0], formatter);
        } catch (Exception e) {
            return LocalDate.of(2024, 12, 13);
        }
    }
}
