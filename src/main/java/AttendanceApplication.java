import controller.AttendanceController;
import domain.CrewAttendanceRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {

    public static void main(String[] args) {
        LocalDate currentDate = parseToDate(args);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        CrewAttendanceRepository crewAttendanceRepository =
                CrewAttendanceRepository.of(currentDate, "src/main/resources/attendances.csv");
        AttendanceController controller = new AttendanceController(inputView, outputView, crewAttendanceRepository);

        controller.run(currentDate);
    }

    private static LocalDate parseToDate(String[] args) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(args[0], formatter);
        } catch (Exception e) {
            return LocalDate.of(2024, 12, 13);
        }
    }
}
