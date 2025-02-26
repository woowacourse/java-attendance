package controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ModifyAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ModifyAttendanceRequest of(String nickname, LocalDate date, int day, String time) {
        return new ModifyAttendanceRequest(nickname, date.withDayOfMonth(day), LocalTime.parse(time, TIME_FORMATTER));
    }
}
