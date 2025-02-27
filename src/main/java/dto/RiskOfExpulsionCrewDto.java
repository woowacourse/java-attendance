package dto;

public record RiskOfExpulsionCrewDto(
        String crewName,
        int absenceCount,
        int lateCount,
        String expulsionStatus
) {
}
