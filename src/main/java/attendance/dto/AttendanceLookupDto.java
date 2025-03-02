package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.CrewStatistic;
import java.util.ArrayList;
import java.util.List;

public record AttendanceLookupDto(String crewName, List<AttendanceLookupRecord> attendanceLookupRecords,
                                  int crewSafeCount, int crewLateCount, int crewAbsentCount, String crewPenalty) {
    public static AttendanceLookupDto fromCrewInformation(Attendances crewAttendances, CrewStatistic crewStatistic) {
        List<AttendanceLookupRecord> attendanceLookupRecords = new ArrayList<>();
        String crewName = crewAttendances.getAttendances().getFirst().getCrewName();
        for (Attendance attendance : crewAttendances.getAttendances()) {
            attendanceLookupRecords.add(AttendanceLookupRecord.fromCrewAttendance(attendance));
        }
        int crewSafeCount = crewStatistic.getCrewSafeCount();
        int crewLateCount = crewStatistic.getCrewLateCount();
        int crewAbsentCount = crewStatistic.getCrewAbsentCount();
        String crewPenalty = crewStatistic.getCrewPenalty();
        return new AttendanceLookupDto(crewName, attendanceLookupRecords, crewSafeCount, crewLateCount, crewAbsentCount,
                crewPenalty);
    }
}
