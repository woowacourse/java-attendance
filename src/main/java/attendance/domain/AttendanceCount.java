package attendance.domain;

public class AttendanceCount {
    private static final int LATE_COUNT_UNIT_FOR_PENALTY = 3;

    private int safeCount;
    private int lateCount;
    private int absentCount;

    public void checkAttendanceCount(final String attendanceType) {
        if (attendanceType.equals(AttendanceType.SAFE.toString())) {
            safeCount++;
            return;
        }
        if (attendanceType.equals(AttendanceType.LATE.toString())) {
            lateCount++;
            return;
        }
        if (attendanceType.equals(AttendanceType.ABSENT.toString())) {
            absentCount++;
        }
    }

    public void resetAttendanceCount() {
        safeCount = 0;
        lateCount = 0;
        absentCount = 0;
    }

    public int calculatePenaltyCount() {
        return absentCount + lateCount / LATE_COUNT_UNIT_FOR_PENALTY;
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
}
