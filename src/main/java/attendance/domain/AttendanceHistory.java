package attendance.domain;

import java.util.List;
import java.util.Map;

public record AttendanceHistory(String nickname, List<String> attendanceHistories,
                                Map<AttendanceStatus, Integer> statusMap) {
}
