package domain;

public class UpdatedAttendanceSnapshot {
    private final Attendance before;
    private final Attendance after;

    public UpdatedAttendanceSnapshot(final Attendance before, final Attendance after){
        this.before = before;
        this.after = after;
    }

    public Attendance getBefore() {
        return before;
    }

    public Attendance getAfter() {
        return after;
    }
}
