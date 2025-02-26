package attendance.dto;

import attendance.domain.risk.RiskType;

public class AttendanceState implements Comparable<AttendanceState> {

    private final String nickname;
    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;
    private final RiskType riskTyp;

    public AttendanceState(String nickname, int attendanceCount, int lateCount, int absenceCount) {
        this.nickname = nickname;
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.riskTyp = RiskType.parse(absenceCount, lateCount);
    }

    public String getNickname() {
        return nickname;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public RiskType getRiskTyp() {
        return riskTyp;
    }

    @Override
    public int compareTo(AttendanceState other) {
        if (compareRiskType(other) == 0 && compareAbsenceScore(other) == 0) {
            return compareNickname(other);
        }
        if (compareRiskType(other) == 0) {
            return compareAbsenceScore(other);
        }
        return compareRiskType(other);
    }

    private int compareRiskType(AttendanceState other) {
        return this.riskTyp.compareTo(other.getRiskTyp());
    }

    private int compareAbsenceScore(AttendanceState other) {
        int currentScore = absenceCount * 3 + lateCount;
        int otherScore = other.getAbsenceCount() * 3 + other.getLateCount();
        return currentScore - otherScore;
    }

    private int compareNickname(AttendanceState other) {
        return nickname.compareTo(other.nickname);
    }
}
