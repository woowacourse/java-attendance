import controller.AttendanceController;
import domain.AttendanceStorage;
import java.io.FileNotFoundException;
import service.AttendanceService;
import view.AttendanceFileReader;
import view.OutputView;

public class AttendanceApplication {
    // TODO: AttendanceDateTime으로 모두 통일? VS 필요 없을때는 LocalDateTime
    // TODO: 10:1 입력 처리, 즉 format이 HH:M 등 일때 ..
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
