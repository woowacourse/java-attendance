package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final LocalDate attendDate;
    private LocalTime attendTime;

    public Attendance(LocalDate attendDate, LocalTime attendTime) {
        this.attendDate = attendDate;
        this.attendTime = attendTime;
    }

    public boolean isSameDate(LocalDate attendDate) {
        return this.attendDate.isEqual(attendDate);
    }

    public void updateTime(LocalTime updateTime) {
        this.attendTime = updateTime;
    }

    public boolean isAbsent() {
        return attendTime.isAfter(LocalTime.of(10, 30));
    }

    public boolean isLate() {
        return attendTime.isAfter(LocalTime.of(10, 5));
    }

    public boolean isAttend() {
        return !isAbsent() && !isLate();
    }

    public boolean isBefore(LocalDate date) {
        return this.attendDate.isBefore(date);
    }

    public int getMonth() {
        return this.attendDate.getMonth().getValue();
    }

    public int getDay() {
        return this.attendDate.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return this.attendDate.getDayOfWeek();
    }

    public int getHour() {
        return this.attendTime.getHour();
    }

    public int getMinute() {
        return this.attendTime.getMinute();
    }
}
