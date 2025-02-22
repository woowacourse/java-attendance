package attendance.domain;

public final class RiskStatistic {

    private final String nickname;
    private final int attendanceCount;
    private final int expulsionCount;
    private final int lateCount;
    private final RiskType riskType;

    public RiskStatistic(String nickname, int attendanceCount, int expulsionCount, int lateCount) {
        this.attendanceCount = attendanceCount;
        this.nickname = nickname;
        this.expulsionCount = expulsionCount;
        this.lateCount = lateCount;
        this.riskType = RiskType.find(expulsionCount, lateCount);
    }

    public String getNickname() {
        return nickname;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getExpulsionCount() {
        return expulsionCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public RiskType getRiskType() {
        return riskType;
    }
}
