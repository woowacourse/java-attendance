package dto;

public record PenaltyCrewResponse(
        String name,
        int lateCount,
        int absentCount,
        String penalty
) {
}