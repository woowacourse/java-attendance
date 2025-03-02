package domain;

import domain.utils.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDate {
    private LocalDate date;
    private LocalTime time;
    private AttendanceStatus status;

    public AttendanceDate(LocalDateTime dateTime) {
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
        saveAttendanceStatus();
    }

    public AttendanceDate(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
        saveAttendanceStatus();
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public void editAttendanceTime(LocalTime newTime) {
        time = newTime;
        saveAttendanceStatus();
    }

    private void saveAttendanceStatus() {
        status = AttendanceStatus.evaluateAttendance(date, time);
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
