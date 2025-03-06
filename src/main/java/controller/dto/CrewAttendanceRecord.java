package controller.dto;

import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.AttendanceStatusCounter;
import domain.CrewPenaltyPolicy;
import java.util.List;

public record CrewAttendanceRecord(
        List<AttendanceResult> attendanceResults,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        CrewPenaltyPolicy crewPenaltyPolicy
) {
    public static CrewAttendanceRecord of(AttendanceRecords attendanceRecords, AttendanceStatusCounter counter, CrewPenaltyPolicy crewPenaltyPolicy) {
        List<AttendanceResult> attendanceResults = attendanceRecords.attendances().stream()
                .map(AttendanceResult::from)
                .toList();
        int attendanceCount = counter.retrieveAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        int lateCount = counter.retrieveAttendanceStatusCount(AttendanceStatus.LATE);
        int absenceCount = counter.retrieveAttendanceStatusCount(AttendanceStatus.ABSENCE);
        return new CrewAttendanceRecord(attendanceResults, attendanceCount, lateCount, absenceCount, crewPenaltyPolicy);
    }
}
