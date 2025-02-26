package attendance.domain.model;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AttendanceCounter {

    private final Map<AttendanceType, Integer> attendanceByType;

    public AttendanceCounter(final List<LocalDateTime> history) {
        this.attendanceByType = initialize();

        for (LocalDateTime attendanceTime : history) {
            attendanceByType.merge(AttendanceType.from(attendanceTime), 1, Integer::sum);
        }
    }

    private Map<AttendanceType, Integer> initialize() {
        Map<AttendanceType, Integer> attendanceByType = new EnumMap<>(AttendanceType.class);
        attendanceByType.put(AttendanceType.ATTENDANCE, 0);
        attendanceByType.put(AttendanceType.LATE, 0);
        attendanceByType.put(AttendanceType.ABSENCE, 0);
        return attendanceByType;
    }

    public int getAttendanceCount() {
        return attendanceByType.get(AttendanceType.ATTENDANCE);
    }

    public int getAbsentCount() {
        return attendanceByType.get(AttendanceType.ABSENCE);
    }

    public int getLateCount() {
        return attendanceByType.get(AttendanceType.LATE);
    }
}
