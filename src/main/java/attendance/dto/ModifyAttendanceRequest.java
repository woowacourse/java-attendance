package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import attendance.util.DateTimeUtil;

public record ModifyAttendanceRequest(
    String name,
    LocalDate date,
    LocalTime time
) {

    public static ModifyAttendanceRequest of(String name, String day, String time, LocalDate date) {
        return new ModifyAttendanceRequest(
            name,
            DateTimeUtil.convertToLocalDate(date, convertToInt(day)),
            DateTimeUtil.convertToLocalTime(time)
        );
    }

    private static int convertToInt(String day) {
        try {
            return Integer.parseInt(day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("{%s}는 잘못된 날짜입니다.", day));
        }
    }
}
