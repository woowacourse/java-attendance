package attendance;

import attendance.domain.AttendanceHistory;
import attendance.controller.AttendanceController;
import attendance.utils.AttendanceFileReader;
import attendance.view.OutputView;
import java.util.List;
import attendance.view.InputView;

public class Application {

    public static void main(String[] args) {
        List<String> fileReadResult = AttendanceFileReader.readCrewAttendances();
        AttendanceHistory attendanceHistory = AttendanceHistory.create(fileReadResult);
        AttendanceController attendanceController = new AttendanceController(InputView.create(),
            OutputView.create(), attendanceHistory, new SystemDate());
        attendanceController.start();
    }
}
