package attendance.domain;

public record CrewAttendanceInformation(String crewName, int attend, int late, int absent, String academicStatus) {
}
