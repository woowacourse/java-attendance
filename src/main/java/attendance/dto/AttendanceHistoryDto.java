package attendance.dto;

public record AttendanceHistoryDto(int absentCount, int lateCount, String expulsionStatus) {}
