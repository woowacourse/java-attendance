package domain;

public record CrewSummary(String nickname,
                          int absenceCount,
                          int tardinessCount,
                          int attendanceCount,
                          int adjustedAbsenceCount,
                          Punishment punishment) {
}
