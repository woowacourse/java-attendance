package domain.attendance;

import domain.attendance.constant.AttendanceRiskLevel;

public class AttendanceCounts {

    private final int attendanceCount;
    private final int tardinessCount;
    private final int absenceCount;

    private AttendanceCounts(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public static AttendanceCounts ofStatusCounts(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        return new AttendanceCounts(attendanceCount, tardinessCount, absenceCount);
    }

    public AttendanceRiskLevel calculateAttendanceRiskLevel() {
        int totalRiskLimitCount = absenceCount + tardinessCount / 3;
        if (totalRiskLimitCount > 5) {
            return AttendanceRiskLevel.EXPULSION;
        }
        if (totalRiskLimitCount >= 3) {
            return AttendanceRiskLevel.COUNSELING;
        }
        if (totalRiskLimitCount >= 2) {
            return AttendanceRiskLevel.WARNING;
        }
        return AttendanceRiskLevel.NORMAL;
    }

    public int getTardinessAndAbsenceCount() {
        return tardinessCount + absenceCount;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getTardinessCount() {
        return tardinessCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
