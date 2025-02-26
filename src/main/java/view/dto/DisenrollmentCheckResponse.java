package view.dto;

import domain.CrewStatus;

public record DisenrollmentCheckResponse(
        String name,
        int totalAbsenceCount,
        int absenceCount,
        int lateCount,
        CrewStatus crewStatus
) {
}
