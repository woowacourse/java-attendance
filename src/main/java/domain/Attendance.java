package domain;

import static util.Constants.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import util.Constants;

public class Attendance {
    private static final String NOT_RUNNING_TIME_ERROR = "[ERROR] 캠퍼스 운영시간이 아닙니다.";
    private static final String HOLIDAY_ERROR = "[ERROR] 주말 및 공휴일은 출석할 수 없습니다.";
    private final LocalDateTime value;

    public Attendance(LocalDateTime value) {
        validateDate(value.toLocalDate());
        validateTime(value.toLocalTime());
        this.value = value;
    }

    public Attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        validateTime(time);
        this.value = LocalDateTime.of(date, time);
    }

    private void validateDate(LocalDate date) {
        if(isHoliday(date)) {
            throw new IllegalArgumentException(HOLIDAY_ERROR);
        }
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException(NOT_RUNNING_TIME_ERROR);
        }
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

    private boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || Constants.HOLIDAY.contains(date.getDayOfMonth());
    }
}
