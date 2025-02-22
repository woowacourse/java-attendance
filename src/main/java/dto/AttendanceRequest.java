package dto;

import java.time.LocalTime;

import util.DateTimeUtil;

public record AttendanceRequest(
    String nickname,
    LocalTime time
) {

    public static AttendanceRequest of(String nickname, String time) {
        return new AttendanceRequest(nickname, DateTimeUtil.convertToTime(time));
    }
}
