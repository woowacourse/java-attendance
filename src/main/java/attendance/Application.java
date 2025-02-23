package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CrewAttendanceManager;
import attendance.domain.Crews;
import attendance.utils.AttendanceFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.BufferedReader;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        Crews crews = Crews.create();
        BufferedReader file = AttendanceFileReader.read();
        CrewAttendanceManager crewAttendanceManager = CrewAttendanceManager.create();
        AttendanceFileReader.initializeAttendances(file, crews, crewAttendanceManager);
        AttendanceController attendanceController = new AttendanceController(new InputView(),
            crews, new OutputView(), new SystemCurrentDate(), crewAttendanceManager);
        attendanceController.start();
    }
}
