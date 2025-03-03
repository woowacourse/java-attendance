package controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import util.DateTimeUtil;

public record SaveAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    public static SaveAttendanceRequest of(String nickname, LocalDate date, String time) {
        return new SaveAttendanceRequest(nickname, date, DateTimeUtil.convertToLocalTime(time));
    }
}
