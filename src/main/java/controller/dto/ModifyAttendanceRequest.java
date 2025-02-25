package controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ModifyAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    public static ModifyAttendanceRequest of(String nickname, LocalDate date, int day, LocalTime time) {
        return new ModifyAttendanceRequest(nickname, date.withDayOfMonth(day), time);
    }
}
