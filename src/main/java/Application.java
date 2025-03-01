import attendance.AttendanceHistory;
import controller.AttendanceController;
import java.util.List;
import utils.AttendanceFileReader;
import view.InputView;

public class Application {

    public static void main(String[] args) {
        List<String> fileReadResult = AttendanceFileReader.readCrewAttendances();
        AttendanceHistory attendanceHistory = AttendanceHistory.create(fileReadResult);
        AttendanceController attendanceController = new AttendanceController(InputView.create(), attendanceHistory);
        attendanceController.start();
    }
}
