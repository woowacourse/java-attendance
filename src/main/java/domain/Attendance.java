package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime dateTime;
    private final boolean isEmpty;

    // 실제 생성
    public Attendance(final String time) {
        this.dateTime = LocalDateTime.parse(time, FORMATTER);
        isEmpty = false;
    }

    // 초기화
    public Attendance(final LocalDateTime dateTime, final boolean isEmpty) {
        this.dateTime = dateTime;
        this.isEmpty = isEmpty;
    }

    public static Attendance of(final String dateTime) {
        return new Attendance(dateTime);
    }

    public static Attendance of(final LocalDateTime dateTime) {
        return new Attendance(dateTime, false);
    }

    public static Attendance empty(final LocalDateTime dateTime) {
        return new Attendance(dateTime, true);
    }

    public Attendance(final Attendance attendance) {
        final LocalDateTime targetDateTime = attendance.dateTime;
        this.dateTime = LocalDateTime.of(targetDateTime.toLocalDate(), targetDateTime.toLocalTime());
        this.isEmpty = attendance.isEmpty;
    }


    public LocalDateTime getDateTime() {
        return LocalDateTime.of(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public AttendanceStatus calculateStatus() {
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceStatus.of(dateTime, 13, 0);
        }
        return AttendanceStatus.of(dateTime, 10, 0);

    }

    public boolean matchDate(final LocalDate localDate) {
        return dateTime.toLocalDate().equals(localDate);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Attendance) {
            final Attendance target = (Attendance) o;
            return dateTime.toLocalDate().equals(target.dateTime.toLocalDate());
        }

        return false;
    }
}
