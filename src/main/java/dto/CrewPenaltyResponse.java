package dto;

public record CrewPenaltyResponse(
        String name,
        int absentCount,
        int lateCount,
        String penalty
) {
}
