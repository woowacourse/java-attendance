package attendance.controller;

import attendance.domain.Attendance;
import attendance.repository.AttendanceRepository;
import attendance.utils.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        AttendanceRepository attendanceRepository = new AttendanceRepository(getAttendanceRecordContent());
    }

    private List<Attendance> getAttendanceRecordContent() {
        List<String> attendanceRecordContents = FileReader.parseToFile("src/main/resources/attendances.csv");
        attendanceRecordContents.removeFirst();
        final List<Attendance> attendances = new ArrayList<>();
        for (String content : attendanceRecordContents) {
            String[] split = content.split(",");

            String crewName = split[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(split[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            attendances.add(new Attendance(crewName, attendanceTime));
        }

        return attendances;
    }
}
