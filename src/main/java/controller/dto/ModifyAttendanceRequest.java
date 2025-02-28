package controller.dto;

import static util.DateTimeUtil.TIME_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record ModifyAttendanceRequest(
        String nickname,
        LocalDate date,
        LocalTime timeToModify
) {

    public static ModifyAttendanceRequest of(String nickname, LocalDate today, int day, String time) {
        validateDay(today, day);
        validateTime(time);
        return new ModifyAttendanceRequest(nickname, today.withDayOfMonth(day), LocalTime.parse(time, TIME_FORMAT));
    }

    private static void validateDay(LocalDate today, int day) {
        try {
            today.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(day + ": 이 달에는 존재하지 않는 날짜입니다.");
        }
    }

    private static void validateTime(String time) {
        try {
            LocalTime.parse(time, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(time + ": 올바르지 않은 시간 형식입니다.");
        }
    }
}
