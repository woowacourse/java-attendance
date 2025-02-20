import controller.AttendanceController;
import domain.CsvReader;

public class AppConfig {
    public AttendanceController controller() {
        return new AttendanceController(fileReader());
    }

    private CsvReader fileReader() {
        return new CsvReader();
    }
}