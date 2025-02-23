package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.Constants;

public class AttendanceDateTime {

    //    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String TIME_FORMAT = "HH:mm";

    private final LocalDateTime dateTime;

    private AttendanceDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime of(final LocalDateTime dateTime) {
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime of(final LocalDate localDateInput, LocalTime timeInput) {
        LocalDateTime dateTime = LocalDateTime.of(localDateInput, timeInput);
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime ofTimeString(final LocalDate localDateInput, String timeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            final LocalTime parsedTime = LocalTime.parse(timeString, formatter);
            LocalDateTime dateTime = LocalDateTime.of(localDateInput, parsedTime);
            return new AttendanceDateTime(dateTime);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException("시간은 HH:mm 형식으로 들어와야 합니다.");
        }
    }

    public static LocalDate parsedLocalDateByDateOfMonth(int dateOfMonth) {
        try {
            return LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, dateOfMonth);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException(
                    String.format("일은 1부터 %d사이의 숫자만 가능합니다.", Constants.LENGTH_OF_MONTH));
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public static LocalDateTime getDefaultDateTime() {
        return LocalDateTime.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, Constants.FIXED_DATE, 0, 0, 0, 0);
    }

    public static LocalDate getDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }

    public static LocalTime getTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    public static Integer getDayOfMonth(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }
}
