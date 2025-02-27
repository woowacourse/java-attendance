package domain;

import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;

    public Attendances(Map<String, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public List<Attendance> getLogsWithName(String nickName) {
        return attendances.get(nickName);
    }

    public int calculateAttendanceCount(String nickName) {
        List<Attendance> logs = getLogsWithName(nickName);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int calculateLateCount(String nickName) {
        List<Attendance> logs = getLogsWithName(nickName);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.LATENESS)
                .count();
    }

    public int calculateAbsentCount(String nickName) {
        List<Attendance> logs = getLogsWithName(nickName);
        return (int) logs.stream()
                .filter(attendance -> attendance.judge() == AttendanceStatus.ABSENCE)
                .count();
    }
}
