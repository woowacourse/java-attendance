package controller.dto;

import domain.attendance.PenaltyType;
import java.util.Map;

public record AttendanceHistoryWithPenaltyTypeDto(
        Map<Integer, AttendanceHistoryDto> historyDtoOfDay,
        PenaltyType penaltyType
) {
}
