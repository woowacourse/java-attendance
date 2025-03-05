package model;

import static model.AttendanceTime.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceHistoryByDate(
        LocalDate date, LocalTime time, AttendanceType type
) {
    public static AttendanceHistoryByDate getDefault(final LocalDate date) {
        return new AttendanceHistoryByDate(date, DEFAULT_TIME, AttendanceType.ABSENT);
    }
}
