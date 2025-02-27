package domain;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {
    private static final List<Integer> HOLIDAYS = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
    private final LocalDateTime dateTime;

    public Attendance(LocalDateTime dateTime) {
        validateDateTime(dateTime);
        this.dateTime = dateTime;
    }

    private void validateDateTime(LocalDateTime dateTime) {
        if (HOLIDAYS.contains(dateTime.getDayOfMonth())) {
            throw new IllegalArgumentException(
                    String.format("12월 %d일 %s요일은 등교일이 아닙니다.",
                            dateTime.getDayOfMonth(),
                            dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public AttendanceStatus calculateAttendanceStatus() {
        return AttendanceStatus.calculateAttendanceStatus(dateTime);
    }

    public boolean isBefore(LocalDateTime today) {
        return dateTime.isBefore(today);
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
