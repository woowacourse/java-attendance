package attendance.domain.dto;

import attendance.domain.RiskType;

public record AttendanceState(
        int attendanceCount,
        int lateCount,
        int absenceCount,
        RiskType riskType
) {

}
