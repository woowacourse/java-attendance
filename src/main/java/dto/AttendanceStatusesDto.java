package dto;

import domain.AttendanceStatus;
import domain.RiskStatus;

import java.time.LocalDate;
import java.util.Map;

public record AttendanceStatusesDto(
        Map<LocalDate, AttendanceStatus> attendanceStatuses,
        int attendCount,
        int tardyCount,
        int absenceCount,
        RiskStatus riskStatus
) {
}
