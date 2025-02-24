package dto;

import java.time.LocalTime;

import util.DateTimeUtil;

public record AttendanceRequestDto(
    String nickname,
    LocalTime time
) {

    public static AttendanceRequestDto of(String nickname, String time) {
        return new AttendanceRequestDto(nickname, DateTimeUtil.convertToTime(time));
    }
}
