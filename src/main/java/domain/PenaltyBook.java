package domain;

public record PenaltyBook(Crew crew, int lateness, int absence, PenaltyType penaltyType) {
}
