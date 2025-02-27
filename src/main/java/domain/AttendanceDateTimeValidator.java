package domain;

import config.Holiday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.DateUtils;

public class AttendanceDateTimeValidator {

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    public static void validate(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        if (DateUtils.isWeekend(date)
            || Holiday.isHoliday(date)
            || time.isBefore(CAMPUS_OPEN_TIME)
            || time.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException();
        }
    }
}
