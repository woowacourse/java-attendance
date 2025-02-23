package attendance.domain;

import attendance.utility.DateTimeParser;
import attendance.utility.FileUtil;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceInit {

    private static final String INIT_FILE_NAME = "attendances.csv";
    private static final String INFO_DELIMITER = ",";

    private final AttendanceManager attendanceManager;

    public AttendanceInit(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public void initAttendances() {
        List<String> lines = FileUtil.readFile(INIT_FILE_NAME);
        lines.forEach(this::initAttendance);
    }

    private void initAttendance(String line) {
        List<String> attendanceInfo = List.of(line.split(INFO_DELIMITER));

        String nickname = attendanceInfo.getFirst();
        LocalDateTime dateTime = DateTimeParser.parseDateTime(attendanceInfo.get(1));

        addNewCrew(nickname);
        insertAttendance(nickname, dateTime);
    }

    private void addNewCrew(String nickname) {
        if (!attendanceManager.containsNickname(nickname)) {
            attendanceManager.addCrew(nickname);
        }
    }

    private void insertAttendance(String nickname, LocalDateTime dateTime) {
        Attendances attendances = attendanceManager.findCrewAttendance(nickname);

        attendances.deleteAttendance(dateTime.toLocalDate());
        attendances.addAttendance(dateTime);
    }
}
