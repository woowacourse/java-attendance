package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.DateTimeParser;

public class Attendance {
    private final LocalDateTime dateTime;
    private final boolean isEmpty;

    public Attendance(final LocalDateTime dateTime, final boolean isEmpty) {
        this.dateTime = dateTime;
        this.isEmpty = isEmpty;
    }

    public Attendance(final LocalDateTime localDateTime) {
        this(localDateTime, false);
    }

    public static Attendance of(final String dateTime) {
        return new Attendance(DateTimeParser.parseToLocalDateTime(dateTime));
    }

    public static Attendance of(final LocalDateTime dateTime) {
        return new Attendance(dateTime, false);
    }

    public static Attendance empty(final LocalDateTime dateTime) {
        return new Attendance(dateTime, true);
    }

    public Attendance(final Attendance attendance) {
        this(attendance.dateTime, attendance.isEmpty());
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public AttendanceStatus calculateStatus() {
        return AttendanceStatus.of(dateTime);
    }

    public boolean matchDate(final LocalDate localDate) {
        return dateTime.toLocalDate().equals(localDate);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Attendance target)) {
            return false;
        }
        return dateTime.toLocalDate().equals(target.dateTime.toLocalDate());

    }
}
