import controller.AttendanceController;
import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import service.AttendanceService;
import view.AttendanceFileReader;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        try {
            AttendanceStorage attendanceStorage = new AttendanceStorage();
            AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);

            AttendanceService attendanceService = new AttendanceService(attendanceStorage);
            AttendanceController attendanceController = new AttendanceController(attendanceService);
            attendanceController.run();
        } catch (FileNotFoundException exception) {
            OutputView.printErrorMessage("attendances.csv 파일을 찾을 수 없습니다. 프로그램을 종료합니다.");
        }
    }
}
