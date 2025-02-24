package attendance.dto;

public record CrewNameAndAcademicStatusDTO(String crewName, int attend, int late, int absent, String academicStatus) {
}
