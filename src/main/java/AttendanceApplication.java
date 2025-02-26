import controller.AttendanceController;
import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import service.AttendanceService;
import view.AttendanceFileReader;

public class AttendanceApplication {
    // TODO: AttendanceDateTime으로 모두 통일? VS 필요 없을때는 LocalDateTime
    // TODO: 10:1 입력 처리, 즉 format이 HH:M 등 일때 ..
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
