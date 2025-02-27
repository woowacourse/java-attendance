package attendance.domain.dto;

public record AttendanceStatusResult(
    int attendanceCount,
    int lateCount,
    int absentCount
) {
    public AttendanceStatusResult(int attendanceCount, int lateCount, int absentCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
    }
}
