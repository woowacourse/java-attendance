package attendance.domain;

import java.time.LocalDateTime;
import java.util.Map;

public record Attendance(Map<LocalDateTime, String> attendance) {
}
