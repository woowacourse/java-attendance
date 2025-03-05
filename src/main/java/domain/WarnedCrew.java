package domain;

public record WarnedCrew(
        String name,
        int tardyCount,
        int absentCount,
        WarningStatus warningStatus
) {
}
