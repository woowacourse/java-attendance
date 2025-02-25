package attendance.domain;

import java.util.List;

public class AttendanceUpdate {

    private final Attendance beforeAttendance;
    private final Attendance afterAttendance;

    public AttendanceUpdate(final List<Attendance> attendances) {
        this.beforeAttendance = attendances.getFirst();
        this.afterAttendance = attendances.getLast();
    }

    public Attendance getBeforeAttendance() {
        return beforeAttendance;
    }

    public Attendance getAfterAttendance() {
        return afterAttendance;
    }
}
