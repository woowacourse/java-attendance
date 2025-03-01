package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceResult {

    private final Map<AttendanceType, Integer> attendanceResult;

    private AttendanceResult() {
        this.attendanceResult = new HashMap<>();
    }

    public static AttendanceResult create() {
        return new AttendanceResult();
    }

    public Map<AttendanceType, Integer> calculateAttendanceResult(AttendanceTimes attendanceTimes) {
        for (AttendanceTime attendanceTime : attendanceTimes.getAttendanceTimes()) {
            AttendanceType attendanceType = AttendanceType.decideAttendanceType(attendanceTime);
            attendanceResult.put(attendanceType, attendanceResult.getOrDefault(attendanceType, 0) + 1);
        }
        return attendanceResult;
    }
}
