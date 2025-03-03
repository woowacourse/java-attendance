package domain.attendance;

import static domain.attendance.constant.AttendanceRiskLevel.COUNSELING;
import static domain.attendance.constant.AttendanceRiskLevel.EXPULSION;
import static domain.attendance.constant.AttendanceRiskLevel.NORMAL;
import static domain.attendance.constant.AttendanceRiskLevel.WARNING;

import domain.attendance.constant.AttendanceRiskLevel;
import domain.attendance.constant.AttendanceStatus;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceCounts {

    private final int attendanceCount;
    private final int tardinessCount;
    private final int absenceCount;

    private AttendanceCounts(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public static AttendanceCounts initCounts() {
        return new AttendanceCounts(0, 0, 0);
    }

    public static AttendanceCounts ofStatusCounts(final int attendanceCount, final int tardinessCount,
                                                  final int absenceCount) {
        return new AttendanceCounts(attendanceCount, tardinessCount, absenceCount);
    }

    public AttendanceCounts plusAttendanceCounts(AttendanceStatus status) {
        if (status == AttendanceStatus.ATTENDANCE) {
            return new AttendanceCounts(attendanceCount + 1, tardinessCount, absenceCount);
        }
        if (status == AttendanceStatus.TARDINESS) {
            return new AttendanceCounts(attendanceCount, tardinessCount + 1, absenceCount);
        }
        if (status == AttendanceStatus.ABSENCE) {
            return new AttendanceCounts(attendanceCount, tardinessCount, absenceCount + 1);
        }
        return new AttendanceCounts(attendanceCount, tardinessCount, absenceCount);
    }

    public AttendanceRiskLevel calculateAttendanceRiskLevel() {
        Map<AttendanceRiskLevel, Integer> riskLevels = new LinkedHashMap<>();
        riskLevels.put(EXPULSION, 6);
        riskLevels.put(COUNSELING, 3);
        riskLevels.put(WARNING, 2);
        int totalRiskLimitCount = absenceCount + tardinessCount / 3;

        return riskLevels.entrySet().stream()
                .filter(entry -> totalRiskLimitCount >= entry.getValue())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(NORMAL);
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
