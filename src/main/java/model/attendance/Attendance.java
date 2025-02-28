package model.attendance;

import common.Common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import model.date.December;
import model.exception.CampusUnavailableException;
import model.exception.FutureAttendanceException;
import model.exception.HolidayAttendanceException;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date) {
        this.date = date;
        this.time = Common.noneAttendanceTime;
    }

    public Attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        validateTime(time);
        this.date = date;
        this.time = time;
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

    private void validateDate(LocalDate date) {
        if (date.isAfter(December.now())) {
            throw new FutureAttendanceException();
        }
        if (December.isHolidayAt(date)) {
            throw new HolidayAttendanceException(date);
        }
    }

    private void validateTime(LocalTime time) {
        if (time.equals(Common.noneAttendanceTime)) {
            return;
        }
        if (time.isBefore(Common.campusOpenTime) || time.isAfter(Common.campusCloseTime)) {
            throw new CampusUnavailableException();
        }
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
