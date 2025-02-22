package dto;

import domain.constants.AttendanceStatus;
import domain.constants.ExpulsionStatus;
import java.util.Map;

public record ExpulsionCrewResponse(
        String name,
        Map<AttendanceStatus, Integer> attendanceStatusCount,
        ExpulsionStatus expulsionStatus
) {
}
