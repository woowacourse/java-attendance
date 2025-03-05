package attendance.domain;

public class AttendanceUpdate {
    private final Attendance beforeAttendance;
    private final Attendance afterAttendance;

    public AttendanceUpdate(Attendance beforeAttendance, Attendance afterAttendance) {
        this.beforeAttendance = beforeAttendance;
        this.afterAttendance = afterAttendance;
    }

    public Attendance getBeforeAttendance() {
        return beforeAttendance;
    }

    public Attendance getAfterAttendance() {
        return afterAttendance;
    }
}
