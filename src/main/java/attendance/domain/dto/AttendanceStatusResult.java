package attendance.domain.dto;

public record AttendanceStatusResult(
    int attendanceCount,
    int lateCount,
    int absentCount,
    String subjectStatus
) {
    public AttendanceStatusResult(int attendanceCount, int lateCount, int absentCount, String subjectStatus) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.subjectStatus = subjectStatus;
    }
}
