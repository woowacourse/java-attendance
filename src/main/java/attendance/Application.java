package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CrewManager;
import attendance.utils.AttendanceFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.BufferedReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        CrewManager crewManager = new CrewManager();
        BufferedReader file = AttendanceFileReader.read();
        AttendanceFileReader.initializeAttendances(file, crewManager);
        AttendanceController attendanceController = new AttendanceController(new InputView(), crewManager, new OutputView(), new SystemCurrentDate());
        attendanceController.start();
    }
}
