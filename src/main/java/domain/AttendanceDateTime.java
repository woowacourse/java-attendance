package domain;

import exception.AttendanceDateTimeExceptionType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.Constants;

public class AttendanceDateTime {

    public static final DateTimeFormatter KOREAN_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm");
    public static final DateTimeFormatter ABSENCE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 dd일 EEEE --:--");

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String TIME_FORMAT = "HH:mm";

    private final LocalDateTime dateTime;

    private AttendanceDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime of(final String input) {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            final LocalDateTime parsedDateTime = LocalDateTime.parse(input, formatter);
            return new AttendanceDateTime(parsedDateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(AttendanceDateTimeExceptionType.INVALID_DATE_TIME_TYPE.getMessage());
        }
    }

    public static AttendanceDateTime of(final LocalDateTime dateTime) {
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime of(final LocalDate localDateInput, final LocalTime timeInput) {
        final LocalDateTime dateTime = LocalDateTime.of(localDateInput, timeInput);
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime ofTimeString(final LocalDate localDateInput, final String timeString) {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            final LocalTime parsedTime = LocalTime.parse(timeString, formatter);
            final LocalDateTime dateTime = LocalDateTime.of(localDateInput, parsedTime);
            return new AttendanceDateTime(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(AttendanceDateTimeExceptionType.INVALID_TIME_TYPE.getMessage());
        }
    }

    public static AttendanceDateTime getDefaultDateTime() {
        final LocalDateTime localDateTime = LocalDateTime.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH,
                Constants.FIXED_DATE, 0, 0, 0, 0);
        return new AttendanceDateTime(localDateTime);
    }

    public static LocalTime getTime(final LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
    }

    public int getDayOfMonth() {
        return dateTime.getDayOfMonth();
    }
}
