package dto;

import domain.AttendanceType;
import domain.PenaltyType;
import java.util.List;
import java.util.Map;

public record AttendanceStatusesOfCrewDto(
        List<AttendanceStatusDto> attendanceStatusDtos,
        Map<AttendanceType, Integer> attendanceTypeCount,
        PenaltyType penaltyType
) {
}
