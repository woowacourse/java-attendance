package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import util.DayUtil;

public record AttendanceModifyRequest (
    String nickname,
    LocalDate date,
    LocalTime time
){

    public static AttendanceModifyRequest of(String nickname, String day, String time) {
        int parsedDay = convertToInt(day);
        return new AttendanceModifyRequest(
            nickname,
            DayUtil.now().withDayOfMonth(parsedDay),
            LocalTime.parse(time, Formatter.TIME_FORMATTER)
        );
    }

    private static int convertToInt(String day) {
        try{
            return Integer.parseInt(day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수가 아닙니다.");
        }
    }
}
