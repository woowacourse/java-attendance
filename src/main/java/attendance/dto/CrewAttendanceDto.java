package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.util.Map;

public record CrewAttendanceDto (
    String name,
    Map<LocalDate, AttendanceInfoDto> dtoMap,
    Map<AttendanceStatus, Integer> statusCounts,
    String penalty,
    LocalDate today
){
    public static CrewAttendanceDto of (String name, Map<LocalDate, AttendanceInfoDto> dtoMap,
                                        Map<AttendanceStatus, Integer> statusCounts, AttendancePenalty penalty, LocalDate today) {
        return new CrewAttendanceDto(name, dtoMap, statusCounts, penalty.getMessage(), today);
    }
}
