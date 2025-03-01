package service.dto;

import domain.AttendanceStatus;
import domain.Crew;
import domain.RiskRank;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

        public static RiskCrew of(Crew crew, Map<AttendanceStatus, Integer> statusCount) {
            return new RiskCrew(crew.getNickname(),
                    statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                    statusCount.getOrDefault(AttendanceStatus.ABSENT, 0),
                    RiskRank.from(statusCount));
        }
    }
}
