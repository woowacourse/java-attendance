package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import java.util.ArrayList;
import java.util.List;

public record AttendanceLookupDto(String crewName, List<AttendanceLookupRecord> attendanceLookupRecords,
                                  int safeCount, int lateCount, int absentCount, String crewPenaltyStatus) {

    public static AttendanceLookupDto fromAttendanceHistoryInfo(Crew crew, CrewStatistic crewStatistic) {
        String crewName = crew.getName();
        List<AttendanceLookupRecord> attendanceLookupRecords = new ArrayList<>();
        Attendances crewAttendances = crewStatistic.getCrewAttendances();
        for (Attendance attendance : crewAttendances.getAttendances()) {
            attendanceLookupRecords.add(AttendanceLookupRecord.fromCrewAttendance(attendance));
        }
        int safeCount = crewStatistic.getSafeCount();
        int lateCount = crewStatistic.getLateCount();
        int absentCount = crewStatistic.getAbsentCount();
        String crewPenaltyStatus = crewStatistic.getCrewStatus().toString();
        return new AttendanceLookupDto(crewName, attendanceLookupRecords, safeCount, lateCount, absentCount,
                crewPenaltyStatus);
    }
}
