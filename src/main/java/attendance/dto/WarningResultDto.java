package attendance.dto;

import attendance.domain.AttendanceReport;
import attendance.domain.Crew;

public record WarningResultDto(String crewName, long absentCount, long lateCount, String status) {

    public static WarningResultDto of(Crew crew, AttendanceReport report) {
        return new WarningResultDto(
                crew.getName(),
                report.countLate(),
                report.countAbsent(),
                report.getWarningStatus().getTitle()
        );
    }
}