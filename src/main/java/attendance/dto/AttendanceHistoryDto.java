package attendance.dto;

import java.util.List;

public record AttendanceHistoryDto(String nickname, List<String> attendanceHistories, String attendanceDismissStatus) {
}
