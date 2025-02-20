package dto;

import domain.RiskStatus;

public record CrewAttendanceStatusResponse(
        String name,
        int attendanceCount,
        int absenceCount,
        int tardyCount,
        RiskStatus riskStatus
) {
}
