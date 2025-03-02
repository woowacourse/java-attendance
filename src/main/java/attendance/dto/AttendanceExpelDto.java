package attendance.dto;

import attendance.domain.CrewStatistic;
import attendance.domain.CrewStatistics;
import java.util.ArrayList;
import java.util.List;

public record AttendanceExpelDto(List<AttendanceExpelRecord> attendanceExpelRecords) {
    public static AttendanceExpelDto fromCrewStatistics(CrewStatistics crewStatistics) {
        List<AttendanceExpelRecord> attendanceExpelRecords = new ArrayList<>();
        for (CrewStatistic crewStatistic : crewStatistics.getCrewStatistics()) {
            attendanceExpelRecords.add(AttendanceExpelRecord.fromCrewStatistic(crewStatistic));
        }
        return new AttendanceExpelDto(attendanceExpelRecords);
    }
}
