package model;

import common.Common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import model.exception.CampusUnavailableException;
import model.exception.FutureAttendanceException;
import model.exception.HolidayAttendanceException;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        validateTime(time);
        this.date = date;
        this.time = time;
    }

    private void validateTime(LocalTime time) {
        if (time.equals(Common.noneAttendanceTime)) {
            return;
        }
        if (time.isBefore(Common.campusOpenTime) || time.isAfter(Common.campusCloseTime)) {
            throw new CampusUnavailableException();
        }
    }

    public boolean isSameDateWith(LocalDate date) {
        return this.date.equals(date);
    }

    public AttendanceStatus findStatus() {
        return AttendanceStatus.findByAttendanceTime(date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attendance that)) {
            return false;
        }
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
