package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private LocalDateTime dateTime;
    private AttendanceType type;

    public Attendance(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Attendance(final LocalDateTime dateTime, final AttendanceType type) {
        this.dateTime = dateTime;
        this.type = type;
    }

    public void calculateAttendanceType() {
        this.type = AttendanceType.of(dateTime);
    }

    public AttendanceType getType() {
        return type;
    }

    public boolean isSameDate(final LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }

    public void modifyDateTime(final LocalDateTime dateTime) {
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
