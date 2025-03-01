package controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import util.DateTimeUtil;

public record ModifyAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    public static ModifyAttendanceRequest of(String nickname, LocalDate today, int day, String time) {
        return new ModifyAttendanceRequest(nickname,
                DateTimeUtil.convertDay(today, day),
                DateTimeUtil.convertToLocalTime(time));
    }
}
