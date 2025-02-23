package attendance.domain.intializer;

import attendance.domain.AttendanceSystem;
import attendance.domain.crew.Crew;
import attendance.domain.crew.CrewStorage;
import attendance.utility.DateTimeParser;
import attendance.utility.FileUtility;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceSystemInitializer {

    private final static String SETTING_FILE_NAME = "attendances.csv";
    private final static String CONTENT_SEPARATOR = ",";

    private final CrewStorage crewStorage;
    private final AttendanceSystem attendanceSystem;

    public AttendanceSystemInitializer(CrewStorage crewStorage, AttendanceSystem attendanceSystem) {
        this.crewStorage = crewStorage;
        this.attendanceSystem = attendanceSystem;
    }

    public void initialize() {
        List<String> lines = FileUtility.readFile(SETTING_FILE_NAME);
        lines.forEach(this::applyAttendanceInfo);
    }

    private void applyAttendanceInfo(String line) {
        List<String> attendanceInfo = List.of(line.split(CONTENT_SEPARATOR));
        String nickname = attendanceInfo.getFirst();
        LocalDateTime dateTime = DateTimeParser.parseDateTime(attendanceInfo.getLast());

        addCrew(nickname);
        addAttendance(nickname, dateTime);
    }

    private void addCrew(String nickname) {
        if (!crewStorage.isContained(nickname)) {
            crewStorage.add(new Crew(nickname));
        }
    }

    private void addAttendance(String nickname, LocalDateTime dateTime) {
        attendanceSystem.saveAttendanceRecord(nickname, dateTime);
    }
}
