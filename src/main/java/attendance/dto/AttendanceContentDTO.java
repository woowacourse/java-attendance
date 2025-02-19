package attendance.dto;

import attendance.domain.Attendance;
import java.util.List;
import java.util.Set;

public record AttendanceContentDTO(List<Attendance> attendances, Set<String> names) {
}
