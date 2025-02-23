package domain;

public class StatisticsResult {

    private final int attendanceCount;
    private final int latenessCount;
    private final int absenceCount;
    private final Penalty penalty;

    public StatisticsResult(int attendanceCount, int latenessCount, int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.latenessCount = latenessCount;
        this.absenceCount = absenceCount;
        this.penalty = Penalty.check(absenceCount, latenessCount);
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLatenessCount() {
        return latenessCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public Penalty getPenalty() {
        return penalty;
    }
}
