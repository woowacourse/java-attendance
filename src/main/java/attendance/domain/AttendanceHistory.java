package attendance.domain;

public class AttendanceHistory {

    private final int absentCount;
    private final int lateCount;
    private final String expulsionStatus;

    public AttendanceHistory(final int absentCount, final int lateCount, String expulsionStatus) {
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.expulsionStatus = expulsionStatus;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public String getExpulsionStatus() {
        return expulsionStatus;
    }
}
