package domain;

public class AttendanceStatics {

    private static final int LATE_TO_ABSENT_RATIO = 3;
    private static final int EXPULSION_STANDARD = 5;
    private static final int INTERVIEW_STANDARD = 3;
    private static final int WARNING_STANDARD = 2;

    private final int attendCount;
    private final int lateCount;
    private final int absentCount;

    public AttendanceStatics(int attendCount, int lateCount, int absentCount) {
        this.attendCount = attendCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
    }

    public boolean isRiskOfExpulsion() {
        AbsentPolicy absentPolicy = calculateAbsentPolicy();
        return absentPolicy == AbsentPolicy.WARNING || absentPolicy == AbsentPolicy.INTERVIEW;
    }

    public AbsentPolicy calculateAbsentPolicy() {
        int totalAbsentCount = absentCount + lateCount / LATE_TO_ABSENT_RATIO;

        if (totalAbsentCount > EXPULSION_STANDARD) {
            return AbsentPolicy.EXPULSION;
        }
        if (totalAbsentCount >= INTERVIEW_STANDARD) {
            return AbsentPolicy.INTERVIEW;
        }
        if (totalAbsentCount == WARNING_STANDARD) {
            return AbsentPolicy.WARNING;
        }
        return AbsentPolicy.NONE;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAttendCount() {
        return attendCount;
    }
}
