package attendance.domain;

public class AttendanceStatusCount {

    private final long attendCount;
    private final long lateCount;
    private final long absenceCount;

    public AttendanceStatusCount() {
        this.attendCount = 0L;
        this.lateCount = 0L;
        this.absenceCount = 0L;
    }

    public AttendanceStatusCount(final long attendCount, final long lateCount, final long absenceCount) {
        this.attendCount = attendCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
    }

    public long getAttendCount() {
        return attendCount;
    }

    public long getLateCount() {
        return lateCount;
    }

    public long getAbsenceCount() {
        return absenceCount;
    }
}
