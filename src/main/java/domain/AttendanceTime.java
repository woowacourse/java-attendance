package domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AttendanceTime {

    private static final String TIME_FORMAT = "HH:mm";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private final LocalTime time;

    private AttendanceTime(final LocalTime time) {
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

    public LocalTime getTime() {
        return time;
    }
}
