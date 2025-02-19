package attendance.domain;

import java.util.List;
import java.util.Map;

public record AttendanceHistory(List<String> attendanceHistories, Map<AttendanceStatus, Integer> status) {
}
