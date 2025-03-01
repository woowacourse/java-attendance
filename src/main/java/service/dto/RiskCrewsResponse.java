package service.dto;

import domain.RiskRank;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public record RiskCrewsResponse(
        List<RiskCrew> riskCrews
) {

    public RiskCrewsResponse {
        riskCrews = sortedRiskCrews(riskCrews);
    }

    private List<RiskCrew> sortedRiskCrews(List<RiskCrew> riskCrews) {
        Function<RiskCrew, Integer> firstSort = riskCrew -> riskCrew.lateCount() + riskCrew.absentCount() * 3;
        Function<RiskCrew, String> secondSort = RiskCrew::nickname;
        return riskCrews.stream().sorted(
                        Comparator.comparing(firstSort, Comparator.reverseOrder())
                                .thenComparing(secondSort))
                .toList();
    }

    public record RiskCrew(
            String nickname,
            int lateCount,
            int absentCount,
            RiskRank riskRank
    ) {

    }
}
