package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AttendanceTime {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");

    private static LocalTime time;

    private AttendanceTime(final LocalTime time) {
        this.time = time;
    }

    public static AttendanceTime of(final String timeInput) {
        LocalTime parsedTime = parse(timeInput);
        return new AttendanceTime(parsedTime);
    }

    private static LocalTime parse(final String timeInput) {
        try {
            return LocalTime.parse(timeInput, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("AttendanceTime 의 형식은 H:m 로 들어와야 합니다");
        }
    }
}
