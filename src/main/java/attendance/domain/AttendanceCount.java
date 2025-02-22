package attendance.domain;

public class AttendanceCount {
    private static final int LATE_COUNT_UNIT = 3;

    private int safeCount;
    private int lateCount;
    private int absentCount;

    public AttendanceCount() {
        this.safeCount = 0;
        this.lateCount = 0;
        this.absentCount = 0;
    }

    public void resetAttendanceCount() {
        this.safeCount = 0;
        this.lateCount = 0;
        this.absentCount = 0;
    }

    public void incrementSafeCount() {
        this.safeCount++;
    }

    public void incrementLateCount() {
        this.lateCount++;
    }

    public void incrementAbsentCount() {
        this.absentCount++;
    }

    public int getSafeCount() {
        return safeCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int calculatePenalty() {
        int plusAbsentCount = lateCount / LATE_COUNT_UNIT;
        absentCount += plusAbsentCount;
        return absentCount;
    }

    public int checkPenaltyCount() {
        return absentCount + lateCount / LATE_COUNT_UNIT;
    }
}
