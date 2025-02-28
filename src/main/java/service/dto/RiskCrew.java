package service.dto;

import domain.RiskRank;

public record RiskCrew(
        String nickname,
        int lateCount,
        int absentCount,
        RiskRank riskRank
) {
    
}
