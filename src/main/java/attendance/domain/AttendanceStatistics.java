package attendance.domain;

public class AttendanceStatistics {
    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;
    public AttendanceStatistics(final int attendanceCount, final int lateCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
    }

    public String calculateDangerousStatus() {
        int totalAbsence = absenceCount;
        totalAbsence += (lateCount / 3);
        if (totalAbsence > 5) {
            return "FIRE";
        }
        if (totalAbsence > 2) {
            return "INTERVIEW";
        }
        if (totalAbsence > 1) {
            return "WARNING";
        }
        return "NONE";
    }
}
