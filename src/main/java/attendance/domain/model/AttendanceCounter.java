package attendance.domain.model;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AttendanceCounter {

    private final Map<AttendanceStatus, Integer> attendanceByType;

    public AttendanceCounter(final List<LocalDateTime> history) {
        this.attendanceByType = initialize();

        for (LocalDateTime attendanceTime : history) {
            attendanceByType.merge(AttendanceStatus.from(attendanceTime), 1, Integer::sum);
        }
    }

    private Map<AttendanceStatus, Integer> initialize() {
        Map<AttendanceStatus, Integer> attendanceByType = new EnumMap<>(AttendanceStatus.class);
        attendanceByType.put(AttendanceStatus.ATTENDANCE, 0);
        attendanceByType.put(AttendanceStatus.LATE, 0);
        attendanceByType.put(AttendanceStatus.ABSENCE, 0);
        return attendanceByType;
    }

    public int getAttendanceCount() {
        return attendanceByType.get(AttendanceStatus.ATTENDANCE);
    }

    public int getAbsentCount() {
        return attendanceByType.get(AttendanceStatus.ABSENCE);
    }

    public int getLateCount() {
        return attendanceByType.get(AttendanceStatus.LATE);
    }
}
