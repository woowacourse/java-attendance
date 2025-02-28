package service.dto;

public record RiskCrew(
        String nickname,
        int lateCount,
        int absentCount,
        String riskRank
) implements Comparable<RiskCrew> {

    @Override
    public int compareTo(RiskCrew o) {
        int riskCount = riskCount();
        int opponentRiskCount = o.riskCount();
        if (riskCount == opponentRiskCount) {
            return nickname.compareTo(o.nickname);
        }
        return opponentRiskCount - riskCount;
    }

    public int riskCount() {
        return lateCount + absentCount * 3;
    }
}
