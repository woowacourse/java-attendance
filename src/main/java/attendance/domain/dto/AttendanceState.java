package attendance.domain.dto;

import attendance.domain.RiskType;

public class AttendanceState {

    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;
    private final RiskType riskTyp;

    public AttendanceState(int attendanceCount, int lateCount, int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.riskTyp = RiskType.parse(absenceCount, lateCount);
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
