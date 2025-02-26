package attendance.dto;

import attendance.domain.CrewStatistic;

public record AttendanceExpelRecord(String crewName, int absentCount, int lateCount, String expectedPenalty) {

    public static AttendanceExpelRecord fromCrewStatistic(CrewStatistic crewStatistic) {
        String crewName = crewStatistic.getCrewName();
        int absentCount = crewStatistic.getAbsentCount();
        int lateCount = crewStatistic.getLateCount();
        String expectedPenalty = crewStatistic.getCrewStatus().toString();
        return new AttendanceExpelRecord(crewName, absentCount, lateCount, expectedPenalty);
    }
}
