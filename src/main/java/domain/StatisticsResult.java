package domain;

public class StatisticsResult {

    private int attendanceCount;
    private int latenessCount;
    private int absenceCount;

    public StatisticsResult(int attendanceCount, int latenessCount, int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.latenessCount = latenessCount;
        this.absenceCount = absenceCount;
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
}
