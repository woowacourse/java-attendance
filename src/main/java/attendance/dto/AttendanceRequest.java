package attendance.dto;

import java.time.LocalTime;

import attendance.util.DateTimeUtil;

public record AttendanceRequest(
    String name,
    LocalTime time
) {
    public static AttendanceRequest of(String name, String time) {
        return new AttendanceRequest(
            name,
            LocalTime.parse(time, DateTimeUtil.TIME_FORMATTER)
        );
    }
}
