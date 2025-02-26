package domain;

public class AttendanceUpdateResult {
    private final Attendance oldAttendance;
    private final Attendance newAttendance;

    AttendanceUpdateResult(Attendance oldAttendance, Attendance newAttendance) {
        this.oldAttendance = oldAttendance;
        this.newAttendance = newAttendance;
    }

    public Attendance getOldAttendance() {
        return oldAttendance;
    }
    public Attendance getNewAttendance() {
        return newAttendance;
    }
}
