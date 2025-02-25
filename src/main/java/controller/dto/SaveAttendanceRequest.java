package controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record SaveAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static SaveAttendanceRequest of(String nickname, LocalDate date, String time) {
        return new SaveAttendanceRequest(nickname, date, LocalTime.parse(time, TIME_FORMAT));
    }
}
