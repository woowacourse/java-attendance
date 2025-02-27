package dto;

public record PenaltyCountResponse(
        int attendCount,
        int lateCount,
        int absentCount
) {
}
