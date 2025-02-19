package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.file.AttendanceFileReader;
import attendance.file.AttendanceFileReader.FileContents;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {
    private Attendances attendances;
    private Crews crews;

    public AttendanceController() throws IOException {
        String path = "src/main/resources/attendances.csv";
        FileContents fileContents = AttendanceFileReader.read(path);
        attendances = fileContents.attendances();
        crews = fileContents.crews();
    }

    public void run() {
        checkAttendance();
    }

    private void checkAttendance() {
        Crew crew = getCrew();
        LocalTime checkInTime = getCheckInTime();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), checkInTime);
        Attendance attendance = Attendance.of(dateTime);
        attendances.addAttendance(crew, attendance);
        OutputView.printAttendanceResult(attendance);
    }

    private Crew getCrew() {
        String inputNickName = InputView.readNickName();
        return crews.getCrew(inputNickName);
    }

    private LocalTime getCheckInTime() {
        String inputCheckInTime = InputView.readCheckInTime();
        return LocalTime.parse(inputCheckInTime);
    }
}
