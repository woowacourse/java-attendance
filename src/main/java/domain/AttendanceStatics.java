package domain;

public class AttendanceStatics {

    private final int attendCount;
    private final int lateCount;
    private final int absentCount;
    private final AbsentPolicy absentPolicy;

    public AttendanceStatics(int attendCount, int lateCount, int absentCount, AbsentPolicy absentPolicy) {
        this.attendCount = attendCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.absentPolicy = absentPolicy;
    }

    public boolean isRiskOfExpulsion() {
        return absentPolicy == AbsentPolicy.WARNING || absentPolicy == AbsentPolicy.INTERVIEW;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public AbsentPolicy getAbsentPolicy() {
        return absentPolicy;
    }

    public int getAttendCount() {
        return attendCount;
    }
}
