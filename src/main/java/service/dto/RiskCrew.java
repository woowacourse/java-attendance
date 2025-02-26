package service.dto;

public record RiskCrew(
        String nickname,
        int lateCount,
        int absentCount,
        String riskRank
) {

}
