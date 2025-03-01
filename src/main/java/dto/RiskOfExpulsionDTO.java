package dto;

import attendance.domain.AcademicStatus;

public record RiskOfExpulsionDTO(String crewName, long late, long absent, AcademicStatus academicStatus) {
}
