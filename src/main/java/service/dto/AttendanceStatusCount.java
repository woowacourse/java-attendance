package service.dto;

public record AttendanceStatusCount(
        int attendanceCount,
        int lateCount,
        int absentCount
) {
    
}
