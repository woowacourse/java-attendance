package attendance.dto;

import attendance.model.AttendanceWarningLevel;
import attendance.model.Crew;

public record CrewAttendanceSummary(
        Crew crew,
        int lateCount,
        int absenceCount,
        AttendanceWarningLevel level
) {
}
