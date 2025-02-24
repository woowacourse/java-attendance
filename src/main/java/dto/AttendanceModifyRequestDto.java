package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import constant.FormatterConstant;
import util.DateTimeUtil;

public record AttendanceModifyRequestDto(
    String nickname,
    LocalDate date,
    LocalTime time
){

    public static AttendanceModifyRequestDto of(String nickname, String day, String time) {
        int parsedDay = convertToInt(day);
        return new AttendanceModifyRequestDto(
            nickname,
            DateTimeUtil.convertToDate(DateTimeUtil.nowDate(), parsedDay),
            LocalTime.parse(time, FormatterConstant.TIME_FORMATTER)
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
