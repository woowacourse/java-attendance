package attendance.domain.initializer;

import attendance.domain.AttendanceSystem;
import attendance.domain.crew.CrewStorage;
import attendance.utility.FileUtility;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceSystemInitializer {

    private static final DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String INITIALIZATION_FILE = "attendance.csv";
    private final CrewStorage crewStorage;
    private final AttendanceSystem attendanceSystem;

    public AttendanceSystemInitializer(CrewStorage crewStorage, AttendanceSystem attendanceSystem) {
        this.crewStorage = crewStorage;
        this.attendanceSystem = attendanceSystem;
    }

    public void initialize() {
        List<String> recordLines = FileUtility.readFile(INITIALIZATION_FILE);
        recordLines.forEach(this::applyRecordLine);
    }

    private void applyRecordLine(String recordLine) {
        List<String> lineContent = List.of(recordLine.split(","));
        String nickname = lineContent.getFirst();
        LocalDateTime dateTime = LocalDateTime.parse(lineContent.getLast(), dateTimeformatter);
        initializerRecord(nickname, dateTime);
    }

    private void initializerRecord(String nickname, LocalDateTime dateTime) {
        if (crewStorage.checkIsNotContained(nickname)) {
            crewStorage.add(nickname);
        }
        attendanceSystem.addAttendanceRecord(nickname, dateTime);
    }
}
