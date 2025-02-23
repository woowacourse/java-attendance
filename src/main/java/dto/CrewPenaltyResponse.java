package dto;

import domain.PenaltyStatus;

public record CrewPenaltyResponse(
        String name,
        int absentCount,
        int lateCount,
        PenaltyStatus penaltyStatus
) {
}