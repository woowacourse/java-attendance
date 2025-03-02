package attendance.domain;

public class AttendanceStatus {
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
        int adjustedAbsences = absentCount + lateCount / 3;
        if (adjustedAbsences > 5) return "제적";
        if (adjustedAbsences >= 3) return "면담";
        if (adjustedAbsences >= 2) return "경고";
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
