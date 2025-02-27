package domain;

import config.Holiday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.DateUtils;

public class AttendanceDateTimeValidator {

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    public static void validateDateTime(LocalDateTime dateTime) {
        validateDate(dateTime.toLocalDate());
        validateTime(dateTime.toLocalTime());
    }

    public static void validateDate(LocalDate date) {
        if (DateUtils.isWeekend(date) || Holiday.isHoliday(date)) {
            throw new IllegalArgumentException();
        }
    }

    public static void validateTime(LocalTime time) {
        if (time.isBefore(CAMPUS_OPEN_TIME) || time.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException();
        }
    }
}
