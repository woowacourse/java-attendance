package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CrewAttendanceManager;
import attendance.domain.Crews;
import attendance.utils.AttendanceFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class Application {

    public static void main(String[] args) throws IOException {
        Crews crews = Crews.create();
        BufferedReader file = AttendanceFileReader.read();
        Map<String, LocalDateTime> fileReadResult = AttendanceFileReader.readCrewAttendances(
            file);
        CrewAttendanceManager crewAttendanceManager = CrewAttendanceManager.create(fileReadResult);
        AttendanceController attendanceController = new AttendanceController(new InputView(),
            crews, new OutputView(), new SystemCurrentDate(), crewAttendanceManager);
        attendanceController.start();
    }
}
