package controller.dto;

import static util.DateTimeUtil.TIME_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record SaveAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime time
) {

    public static SaveAttendanceRequest of(String nickname, LocalDate date, String time) {
        validateTime(time);
        return new SaveAttendanceRequest(nickname, date, LocalTime.parse(time, TIME_FORMAT));
    }

    private static void validateTime(String time) {
        try {
            LocalTime.parse(time, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(time + ": 올바르지 않은 시간 형식입니다.");
        }
    }
}
