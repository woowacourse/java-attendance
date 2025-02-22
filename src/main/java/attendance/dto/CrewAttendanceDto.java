package attendance.dto;

import attendance.domain.AttendancePenalty;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record CrewAttendanceDto (
    String name,
    Map<LocalDate, AttendanceInfoDto> dtoMap,
    List<Integer> counts,
    String penalty,
    LocalDate today
){
    public static CrewAttendanceDto of (String name, Map<LocalDate, AttendanceInfoDto> dtoMap,
                                        List<Integer> counts, AttendancePenalty penalty, LocalDate today) {
        return new CrewAttendanceDto(name, dtoMap, counts, penalty.getMessage(), today);
    }
}
