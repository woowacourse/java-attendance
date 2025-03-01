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
        validateAlreadyAttendance(attendanceTimes.add(attendanceTime));
    }

    public Map<String, AttendanceTimes> getAttendanceHistory() {
        return Collections.unmodifiableMap(attendanceHistory);
    }

    public AttendanceTime getAttendanceTimeByDate(String nickname, int findDate) {
        AttendanceTimes attendanceTimes = attendanceHistory.get(nickname);
        return attendanceTimes.findAttendanceByDate(findDate);
    }

    private void validateAlreadyAttendance(boolean isAdded) {
        if (!isAdded) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
    }
}
