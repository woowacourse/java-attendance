package model.attendance;

import common.Campus;
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
        validateHolidayDate(date);
        this.date = date;
        this.time = Campus.noneAttendanceTime;
    }

    public Attendance(LocalDate date, LocalTime time) {
        validateHolidayDate(date);
        validateFutureDate(date);
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

    private void validateFutureDate(LocalDate date) {
        if (date.isAfter(December.now())) {
            throw new FutureAttendanceException();
        }
    }

    private void validateHolidayDate(LocalDate date) {
        if (December.isHolidayAt(date)) {
            throw new HolidayAttendanceException(date);
        }
    }

    private void validateTime(LocalTime time) {
        if (time.equals(Campus.noneAttendanceTime)) {
            return;
        }
        if (time.isBefore(Campus.campusOpenTime) || time.isAfter(Campus.campusCloseTime)) {
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
