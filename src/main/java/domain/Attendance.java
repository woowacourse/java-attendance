package domain;

import static util.Constants.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private static final String NOT_RUNNING_TIME_ERROR = "[ERROR] 캠퍼스 운영시간이 아닙니다.";
    private final LocalDateTime value;

    public Attendance(LocalDateTime value) {
        validateTime(value.toLocalTime());
        this.value = value;
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException(NOT_RUNNING_TIME_ERROR);
        }
    }

    public Attendance(LocalDate date, LocalTime time) {
        this.value = LocalDateTime.of(date, time);
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
}
