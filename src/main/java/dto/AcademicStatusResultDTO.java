package dto;

import attendance.domain.AcademicStatus;

public record AcademicStatusResultDTO(String crewName, long attend, long late, long absent,
                                      AcademicStatus academicStatus) {
}
