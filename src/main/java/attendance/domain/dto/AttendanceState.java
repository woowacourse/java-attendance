package attendance.domain.dto;

public record AttendanceState(
        int attendanceCount,
        int lateCount,
        int absenceCount
) {

}
