package domain;

public record CrewSummary(String nickname, int absenceCount, int tardinessCount, int adjustedAbsenceCount,
                          Punishment punishment) {
}
