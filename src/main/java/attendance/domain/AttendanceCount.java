package attendance.domain;

import java.util.List;
import java.util.Map;

public class AttendanceCount {

    private final Map<AttendanceStatus, Integer> attendance;

    public AttendanceCount(Map<AttendanceStatus, Integer> attendance) {
        this.attendance = attendance;
    }

    public static AttendanceCount create(List<Attendance> attendances) {
        return null;
    }
}
