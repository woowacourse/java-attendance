package dto;

import domain.AttendCount;
import domain.AttendanceResult;
import java.util.List;

public record AttendResultDto(String name, List<AttendanceResult> attendanceResults, AttendCount attendCount) {
}
