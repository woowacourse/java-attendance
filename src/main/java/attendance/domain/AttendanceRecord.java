package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceRecord {

    private final Map<AttendanceDate, AttendanceTime> attendanceRecord;

    public AttendanceRecord(final Map<AttendanceDate, AttendanceTime> attendanceRecord) {
        validateNotNull(attendanceRecord);
        this.attendanceRecord = new HashMap<>(attendanceRecord);
    }

    private static void validateNotNull(final Map<AttendanceDate, AttendanceTime> attendanceRecord) {
        if (attendanceRecord == null) {
            throw new IllegalArgumentException(
                "출석 기록은 기록을 가지고 있어야 합니다.");
        }

        for (final Map.Entry<AttendanceDate, AttendanceTime> entry : attendanceRecord.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException(
                    "출석 기록은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
            }
        }
    }
}
