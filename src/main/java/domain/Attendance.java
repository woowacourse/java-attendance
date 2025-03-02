package domain;

import static domain.AttendancePolicy.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final LocalDateTime dateAndTime;

    public Attendance(LocalDateTime dateAndTime) {
        validateHoliday(dateAndTime.toLocalDate());
        validateRunningTime(dateAndTime.toLocalTime());
        this.dateAndTime = dateAndTime;
    }

    public Attendance(LocalDate date, LocalTime time) {
        validateHoliday(date);
        validateRunningTime(time);
        this.dateAndTime = LocalDateTime.of(date, time);
    }

    public LocalDate getDate() {
        return dateAndTime.toLocalDate();
    }

    public DayOfWeek getDayOfWeek() {
        return dateAndTime.getDayOfWeek();
    }

    public LocalTime getTime() {
        return dateAndTime.toLocalTime();
    }

    public boolean isSameDateWith(Attendance attendance) {
        return dateAndTime.toLocalDate().equals(attendance.getDate());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Attendance other = (Attendance) object;
        return Objects.equals(dateAndTime, other.dateAndTime);
    }
}
