package domain;

public record PenaltyBook(String name, int lateness, int absence, PenaltyType penaltyType) {
}
