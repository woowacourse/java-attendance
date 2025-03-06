package attendance.domain;

public record WarningCrewDto(
        String crewNickname,
        int absentCount,
        int lateCount,
        int convertedAbsentCount,
        WarningLevel warningLevel
) {
}
