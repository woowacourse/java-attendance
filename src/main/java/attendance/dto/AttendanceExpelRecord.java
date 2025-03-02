package attendance.dto;

import attendance.domain.CrewStatistic;

public record AttendanceExpelRecord(String crewName, int crewAbsentCount, int crewLateCount, String crewPenalty) {
    public static AttendanceExpelRecord fromCrewStatistic(CrewStatistic crewStatistic) {
        String crewName = crewStatistic.getCrewName();
        int crewAbsentCount = crewStatistic.getCrewAbsentCount();
        int crewLateCount = crewStatistic.getCrewLateCount();
        String crewPenalty = crewStatistic.getCrewPenalty();
        return new AttendanceExpelRecord(crewName, crewAbsentCount, crewLateCount, crewPenalty);
    }
}
