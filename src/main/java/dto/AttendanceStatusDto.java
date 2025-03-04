package dto;

import domain.AttendanceType;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import view.OutputView;

public record AttendanceStatusDto(
        int month,
        int day,
        DayOfWeek dayOfWeek,
        String hour,
        String minute,
        AttendanceType attendanceType
) {
    public static AttendanceStatusDto of(LocalDateTime localDateTime, AttendanceType attendanceType) {
        return new AttendanceStatusDto(
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek(),
                String.valueOf(localDateTime.getHour()),
                String.valueOf(localDateTime.getMinute()),
                attendanceType
        );
    }

    public static AttendanceStatusDto generateNotRecordedOf(LocalDateTime localDateTime) {
        return new AttendanceStatusDto(
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek(),
                OutputView.SHOWING_TEXT_WHEN_NOT_RECORDED,
                OutputView.SHOWING_TEXT_WHEN_NOT_RECORDED,
                AttendanceType.ABSENCE
        );
    }
}
