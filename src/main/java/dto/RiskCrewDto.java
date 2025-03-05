package dto;

import domain.RiskStatus;

public record RiskCrewDto(
        String name,
        int absenceCount,
        int tardyCount,
        RiskStatus riskStatus
) {
}
