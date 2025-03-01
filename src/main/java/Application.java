import controller.AttendanceController;
import model.CrewGenerator;
import util.CsvReader;

public class Application {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    public static void main(final String[] args) {

        final AttendanceController attendanceController = new AttendanceController(CrewGenerator.parseCrewAndAttendanceBook(CsvReader.readFile(FILE_PATH)));
        attendanceController.run();
    }
}
