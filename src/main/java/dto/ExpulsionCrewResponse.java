package dto;

import domain.AttendanceStatus;
import domain.ExpulsionStatus;
import java.util.Map;

public record ExpulsionCrewResponse(
        String name,
        Map<AttendanceStatus, Integer> attendanceStatusCount,
        ExpulsionStatus expulsionStatus
) {
}
