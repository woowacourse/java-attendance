package attendance.domain;

public class AttendanceStatus {
    private static final int LATE_TO_ABSENT_RATIO = 3;
    private static final int DISMISSAL_THRESHOLD = 5;
    private static final int COUNSELING_THRESHOLD = 3;
    private static final int WARNING_THRESHOLD = 2;

    private final int attendanceCount;
    private final int lateCount;
    private final int absentCount;
    private final String subjectStatus;

    public AttendanceStatus(int attendanceCount, int lateCount, int absentCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.subjectStatus = determineSubjectStatus();
    }

    private String determineSubjectStatus() {
        int adjustedAbsences = absentCount + lateCount / LATE_TO_ABSENT_RATIO;
        if (adjustedAbsences > DISMISSAL_THRESHOLD) return SubjectStatus.DISMISSED.getStatus();
        if (adjustedAbsences >= COUNSELING_THRESHOLD) return SubjectStatus.COUNSELING.getStatus();
        if (adjustedAbsences >= WARNING_THRESHOLD) return SubjectStatus.WARNING.getStatus();
        return null;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public String getSubjectStatus() {
        return subjectStatus;
    }
}
