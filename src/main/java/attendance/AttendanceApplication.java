package attendance;

import attendance.controller.AttendanceController;
import attendance.repository.AttendanceBookLoader;
import attendance.repository.AttendanceBookRepository;
import attendance.view.input.InputView;
import attendance.view.ouput.OutputView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendanceApplication {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd");

    public static void main(String[] args) {
        final LocalDate currentDate = parseLocalDateOrDefault(
            args, LocalDate.of(2024, 12, 13));
        final AttendanceBookRepository attendanceBookRepository = createAttendanceBookRepository();
        final AttendanceController attendanceController = createAttendanceController(
            attendanceBookRepository);

        attendanceController.run(currentDate);
    }

    private static LocalDate parseLocalDateOrDefault(
        final String[] args,
        final LocalDate defaultDate
    ) {
        try {
            return LocalDate.parse(args[0], DATE_FORMATTER);
        } catch (Exception e) {
            return defaultDate;
        }
    }

    private static AttendanceBookRepository createAttendanceBookRepository() {
        final AttendanceBookLoader attendanceBookLoader = new AttendanceBookLoader(
            FILE_PATH);
        return AttendanceBookRepository.from(
            attendanceBookLoader);
    }

    private static AttendanceController createAttendanceController(final AttendanceBookRepository attendanceBookRepository) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        return new AttendanceController(
            inputView, outputView, attendanceBookRepository);
    }
}
