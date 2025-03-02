import controller.AttendanceController;
import model.AttendanceDateTime;
import model.CrewGenerator;
import util.CsvReader;

public class Application {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static void main(final String[] args) {
        final AttendanceDateTime todayDateTime = AttendanceDateTime.of("2024-12-16 10:00");
        final AttendanceController attendanceController = new AttendanceController(CrewGenerator.parseCrewAndAttendanceBook(todayDateTime, CsvReader.readFile(FILE_PATH)));
        attendanceController.run(todayDateTime);
    }
}
