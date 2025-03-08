package domain.dateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AttendanceTime {

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final String TIME_FORMAT = "HH:mm";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private final LocalTime time;

    private AttendanceTime(final LocalTime time) {
        validateTime(time);
        this.time = time;
    }

    public static AttendanceTime from(final String inputTime) {
        final LocalTime time = parseTime(inputTime);
        return new AttendanceTime(time);
    }

    private static LocalTime parseTime(final String inputTime) {
        try {
            return LocalTime.parse(inputTime, TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식은 hh:mm으로 입력해주세요.");
        }
    }

    private void validateTime(final LocalTime time) {
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException("운영 시간에만 출석이 가능합니다. (운영 시간: 08:00 ~ 23:00)");
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
