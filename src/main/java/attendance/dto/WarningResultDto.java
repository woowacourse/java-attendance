package attendance.dto;

import attendance.domain.AttendanceReport;
import attendance.domain.Crew;

public record WarningResultDto(
        String crewName,
        long absentCount,
        long lateCount,
        String status,
        long effectiveAbsencesCount) {

    public static WarningResultDto of(Crew crew, AttendanceReport report) {
        return new WarningResultDto(
                crew.getName(),
                report.countAbsent(),
                report.countLate(),
                report.getWarningStatus().getTitle(),
                report.countEffectiveAbsences()
        );
    }
}