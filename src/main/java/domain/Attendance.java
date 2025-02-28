package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Attendance {
    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);
    private static final List<Integer> HOLIDAY = List.of(25);

    private final LocalDateTime value;

    public Attendance(LocalDateTime value) {
        this.value = value;
    }

    public Attendance(LocalDate date, LocalTime time) {
        this.value = LocalDateTime.of(date, time);
    }

    public static boolean isInvalidTime(LocalTime time) {
        return time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME);
    }

    public LocalDate getDate() {
        return value.toLocalDate();
    }

    public DayOfWeek getDayOfWeek() {
        return value.getDayOfWeek();
    }

    public LocalTime getTime() {
        return value.toLocalTime();
    }

    public boolean isSameDateWith(Attendance attendance) {
        return value.toLocalDate().equals(attendance.getDate());
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Attendance other = (Attendance) object;
        return Objects.equals(value, other.value);
    }

    public static boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || HOLIDAY.contains(date.getDayOfMonth());
    }
}
