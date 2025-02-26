import controller.AttendanceController;
import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import service.AttendanceService;
import view.AttendanceFileReader;

public class AttendanceApplication {
    // TODO: AttendanceDateTime으로 모두 통일? VS 필요 없을때는 LocalDateTime
    public static void main(String[] args) {
        try {
            AttendanceStorage attendanceStorage = new AttendanceStorage();
            AttendanceFileReader.readAttendanceFile(attendanceStorage);
            AttendanceService attendanceService = new AttendanceService(attendanceStorage);
            AttendanceController attendanceController = new AttendanceController(attendanceService);
            attendanceController.run();
        } catch (FileNotFoundException exception) {
            // TODO:
        }
    }
}
