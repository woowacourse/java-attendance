package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {
    private final LocalDateTime dateTime;

    public Attendance(LocalDateTime dateTime) {
        validateDateTime(dateTime);
        this.dateTime = dateTime;
    }

    private void validateDateTime(LocalDateTime dateTime) {
        if (DayType.calculateDayType(dateTime.getDayOfMonth()) != DayType.WEEKDAY) {
            throw new IllegalArgumentException(
                    String.format("12월 %d일 %s요일은 등교일이 아닙니다.",
                            dateTime.getDayOfMonth(),
                            dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public AttendanceStatus calculateAttendanceStatus() {
        return AttendanceStatus.calculateAttendanceStatus(dateTime);
    }

    public boolean isBefore(LocalDate today) {
        return LocalDate.of(2024, 12, dateTime.getDayOfMonth()).isBefore(today);
    }

    public boolean isSameDay(int day) {
        return dateTime.getDayOfMonth() == day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return dateTime.getDayOfMonth() == that.dateTime.getDayOfMonth();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime.getDayOfMonth());
    }

    @Override
    public int compareTo(Attendance o) {
        return dateTime.compareTo(o.dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
