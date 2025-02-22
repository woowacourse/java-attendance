package attendance.domain;

public final class RiskStatistics {

    private final String nickname;
    private final int expulsionCount;
    private final int lateCount;
    private final RiskType warningType;

    public RiskStatistics(String nickname, int expulsionCount, int lateCount) {
        this.nickname = nickname;
        this.expulsionCount = expulsionCount;
        this.lateCount = lateCount;
        this.warningType = RiskType.find(expulsionCount, lateCount);
    }

    public String getNickname() {
        return nickname;
    }

    public RiskType getWarningType() {
        return warningType;
    }
}
