import controller.AttendanceController;
import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import service.AttendanceService;
import view.AttendanceFileReader;

public class AttendanceApplication {
    public static void main(String[] args) {
        try {
            AttendanceStorage attendanceStorage = new AttendanceStorage();
            AttendanceFileReader.readAttendanceFile(attendanceStorage);
            AttendanceService attendanceService = new AttendanceService(attendanceStorage);
            AttendanceController attendanceController = new AttendanceController(attendanceService);
        } catch (FileNotFoundException exception) {

        }
    }
}
