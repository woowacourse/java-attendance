package attendance.io.file;

import java.time.LocalDateTime;

public record AttendanceInfo(
        String nickname,
        LocalDateTime attendanceDateTime
) {
}
