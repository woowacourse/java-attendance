package dto;

import java.time.LocalTime;

public record AttendanceRequest(
    String nickname,
    LocalTime time
) {
    public static AttendanceRequest of(String nickname, String time) {
        return new AttendanceRequest(nickname, LocalTime.parse(time, Formatter.TIME_FORMATTER));
    }
}
