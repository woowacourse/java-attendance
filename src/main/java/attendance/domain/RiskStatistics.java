package attendance.domain;

public final class RiskStatistics {

    private final String nickname;
    private final int expulsionCount;
    private final int lateCount;
    private final AttendanceWarningType warningType;

    public RiskStatistics(String nickname, int expulsionCount, int lateCount) {
        this.nickname = nickname;
        this.expulsionCount = expulsionCount;
        this.lateCount = lateCount;
        this.warningType = AttendanceWarningType.find(expulsionCount, lateCount);
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceWarningType getWarningType() {
        return warningType;
    }
}
