package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private LocalDateTime dateTime;
    private AttendanceStatus status;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = checkAttendanceStatus(dateTime);
    }

    public Attendance(LocalDateTime dateTime, AttendanceStatus status) {
        this.dateTime = dateTime;
        this.status = status;
    }

    public AttendanceStatus checkAttendanceStatus(LocalDateTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return attend(hour, minute, 13);
        }

        return  attend(hour, minute, 10);
    }

    private AttendanceStatus attend(int hour, int minute, int startHour) {
        if (hour >= startHour) {
            if (hour > startHour || minute > 30) {
                return AttendanceStatus.LATE_ABSENCE;
            }
            if (minute > 5) {
                return AttendanceStatus.LATE;
            }
        }
        return AttendanceStatus.ATTEND;
    }

    public boolean isEqualToDate(LocalDate today) {
        return today.equals(LocalDate.from(dateTime));
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public AttendanceStatus updateDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = checkAttendanceStatus(dateTime);
        return this.status;
    }
}


