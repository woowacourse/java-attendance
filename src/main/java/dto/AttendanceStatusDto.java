package dto;

import domain.AttendanceType;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public record AttendanceStatusDto(
        int month,
        int day,
        DayOfWeek dayOfWeek,
        int hour, int minute,
        AttendanceType attendanceType
) {
    public static AttendanceStatusDto of(LocalDateTime dateTime, AttendanceType attendanceType) {
        return new AttendanceStatusDto(
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek(),
                dateTime.getHour(),
                dateTime.getMinute(),
                attendanceType
        );
    }
}
