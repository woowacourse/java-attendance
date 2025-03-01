package service.dto;

import domain.RiskRank;
import java.util.List;

public record RiskCrewsResponse(
        List<RiskCrew> riskCrews
) {

    public record RiskCrew(
            String nickname,
            int lateCount,
            int absentCount,
            RiskRank riskRank
    ) {

    }
}
