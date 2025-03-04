package dto;

public record PenaltyResponse(
        int attendCount,
        int lateCount,
        int absentCount,
        String penalty
) {
}