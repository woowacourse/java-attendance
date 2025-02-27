package dto;

import domain.AttendanceDateTime;
import domain.AttendanceType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceStatusDto(
        String month,
        String day,
        DayOfWeek dayOfWeek,
        String hour,
        String minute,
        AttendanceType attendanceType
) {
    public static AttendanceStatusDto of(LocalDateTime localDateTime, AttendanceType attendanceType) {
        return new AttendanceStatusDto(
                String.valueOf(localDateTime.getMonthValue()),
                String.valueOf(localDateTime.getDayOfMonth()),
                localDateTime.getDayOfWeek(),
                String.valueOf(localDateTime.getHour()),
                String.valueOf(localDateTime.getMinute()),
                attendanceType
        );
    }

    public static AttendanceStatusDto generateNotRecordedOf(LocalDateTime localDateTime) {
        return new AttendanceStatusDto(
                String.valueOf(localDateTime.getMonthValue()),
                String.valueOf(localDateTime.getDayOfMonth()),
                localDateTime.getDayOfWeek(),
                "--",
                "--",
                AttendanceType.ABSENCE
        );
    }
}
