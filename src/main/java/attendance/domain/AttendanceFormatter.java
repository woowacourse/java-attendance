package attendance.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AttendanceFormatter {

    private String ATTENDANCE_RESULT_FORMAT = "%s (%s)";

    public String formattedTime(LocalDateTime attendanceResult, AttendanceStatus attendanceStatus) {
        String formattedDateTime = attendanceResult.format(DateTimeFormatter.ofPattern("MM월 d일 E요일 HH:mm"));
        return String.format(ATTENDANCE_RESULT_FORMAT, formattedDateTime, attendanceStatus.getStatus());
    }
}
