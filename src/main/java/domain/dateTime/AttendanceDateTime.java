package domain.dateTime;

import static controller.AttendanceCommandController.SYSTEM_DATE_TIME;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class AttendanceDateTime implements Comparable<AttendanceDateTime> {
    private static final String ATTENDANCE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            ATTENDANCE_DATE_TIME_FORMAT);

    private final LocalDateTime dateTime;

    private AttendanceDateTime(final LocalDateTime dateTime) {
        AttendanceDateValidator.validate(dateTime.toLocalDate());
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime from(final LocalDateTime dateTime) {
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime from(final String inputDateTime) {
        final LocalDateTime parsedDateTime = parseDateTime(inputDateTime);
        return new AttendanceDateTime(parsedDateTime);
    }

    public static AttendanceDateTime from(final AttendanceTime attendanceTime) {
        final LocalDate date = SYSTEM_DATE_TIME.toLocalDate();
        final LocalDateTime dateTime = LocalDateTime.of(date, attendanceTime.getTime());
        return new AttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime of(final AttendanceDate date, final AttendanceTime time) {
        final LocalDateTime localDateTime = LocalDateTime.of(date.getDate(), time.getTime());
        return new AttendanceDateTime(localDateTime);
    }

    public static AttendanceDateTime createAbsence(final LocalDate date) {
        final LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.of(0, 0));
        return new AttendanceDateTime(localDateTime);
    }

    private static LocalDateTime parseDateTime(final String inputDateTime) {
        try {
            return LocalDateTime.parse(inputDateTime, DATE_TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식은 yyyy-mm-dd- hh:mm으로 작성해주세요.");
        }
    }

    public boolean isEqualToDayOfWeek(final String inputDayOfWeek) {
        final DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return String.valueOf(dayOfWeek).equals(inputDayOfWeek);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isSameDate(final LocalDate date) {
        final LocalDate localDate = dateTime.toLocalDate();
        return localDate.equals(date);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime);
    }

    @Override
    public int compareTo(final AttendanceDateTime o) {
        return dateTime.compareTo(o.dateTime);
    }
}
