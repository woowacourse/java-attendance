package domain;

public record RiskCrew(
        String nickname,
        int lateCount,
        int absentCount,
        RiskRank riskRank
) {

    public static RiskCrew of(Crew crew, AttendanceStatusCount statusCount) {
        return new RiskCrew(crew.getNickname(),
                statusCount.lateCount(),
                statusCount.absentCount(),
                RiskRank.from(statusCount));
    }
}