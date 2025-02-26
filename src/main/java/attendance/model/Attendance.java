package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

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

    public String getDayOfMonth() {
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
    }

    public String getTime() {
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
