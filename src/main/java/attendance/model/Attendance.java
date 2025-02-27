package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private LocalDateTime dateTime;
    private AttendanceType type;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Attendance(LocalDateTime dateTime, AttendanceType type) {
        this.dateTime = dateTime;
        this.type = type;
    }

    public void calculateAttendanceType() {
        this.type = AttendanceType.of(dateTime);
    }

    public AttendanceType getType() {
        return type;
    }

    public boolean isSameDate(LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }

    public void modifyDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMonth() {
        return String.valueOf(dateTime.getMonthValue());
    }

    public String getDateOfMonth() {
        return String.valueOf(dateTime.getDayOfMonth());
    }

    public DayOfWeek getDayOfMonth() {
        return dateTime.getDayOfWeek();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Attendance createSameAttendance() {
        return new Attendance(dateTime, type);
    }
}
