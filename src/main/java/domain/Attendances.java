package domain;

import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;

    public Attendances(Map<String, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public List<Attendance> getLogsWithName(String nickname) {
        return attendances.get(nickname);
    }

    public int calculateAttendanceCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int calculateLateCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.LATENESS)
                .count();
    }

    public int calculateAbsentCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.ABSENCE)
                .count();
    }
}
