package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;

    public Attendances(Map<String, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public void addAttendanceLog(String nickname, LocalDateTime localDateTime) {
        if (!attendances.containsKey(nickname)) {
            List<Attendance> logs = new ArrayList<>();
            logs.add(new Attendance(localDateTime));
            attendances.put(nickname, logs);
            return;
        }

        List<Attendance> logs = attendances.get(nickname);
        logs.add(new Attendance(localDateTime));
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
