package attendance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistory {

    private final Map<String, AttendanceTimes> attendanceHistory;

    private AttendanceHistory() {
        this.attendanceHistory = new HashMap<>();
    }

    public static AttendanceHistory create() {
        return new AttendanceHistory();
    }

    public void add(String nickname, AttendanceTime attendanceTime) {
        attendanceHistory.computeIfAbsent(nickname, k -> AttendanceTimes.create());
        AttendanceTimes attendanceTimes = attendanceHistory.get(nickname);
        attendanceTimes.add(attendanceTime);
    }

    public Map<String, AttendanceTimes> getAttendanceHistory() {
        return Collections.unmodifiableMap(attendanceHistory);
    }

}
