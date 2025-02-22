package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public enum AttendanceType {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String name;

    AttendanceType(String name) {
        this.name = name;
    }

    public static Map<AttendanceType, Integer> initializeAttendanceResult() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        for (AttendanceType attendanceType : AttendanceType.values()) {
            attendanceResult.put(attendanceType, 0);
        }
        return attendanceResult;
    }

    public String getName() {
        return name;
    }
}
