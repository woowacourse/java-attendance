package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceResult {

    public static Map<AttendanceType, Integer> calculateAttendanceResult(AttendanceTimes attendanceTimes) {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        for (AttendanceTime attendanceTime : attendanceTimes.getAttendanceTimes()) {
            AttendanceType attendanceType = AttendanceType.decideAttendanceType(attendanceTime);
            attendanceResult.put(attendanceType, attendanceResult.getOrDefault(attendanceType, 0) + 1);
        }
        return attendanceResult;
    }
}
