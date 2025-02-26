package service.dto;

import java.util.Map;

public record RiskCrew(
        String nickname,
        Map<String, Integer> attendanceStatusCount,
        String riskRank
) {

}
