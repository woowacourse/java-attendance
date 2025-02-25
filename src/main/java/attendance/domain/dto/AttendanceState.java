package attendance.domain.dto;

import attendance.domain.RiskType;

public class AttendanceState {

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
}
