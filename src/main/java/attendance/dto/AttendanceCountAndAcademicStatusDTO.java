package attendance.dto;

public record AttendanceCountAndAcademicStatusDTO(int attend, int late, int absent, String academicStatus) {
}
