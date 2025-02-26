package dto;

import domain.AttendanceType;
import java.time.DayOfWeek;

public record AttendanceStatusDto(
        int month,
        int day,
        DayOfWeek dayOfWeek,
        int hour, int minute,
        AttendanceType attendanceType
) {
}
