package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    public static final int MONDAY_START_HOUR = 13;
    public static final int START_HOUR = 10;
    public static final int ABSENCE_CRITERIA = 30;
    public static final int LATE_CRITERIA = 5;

    private LocalDateTime dateTime;
    private AttendanceStatus status;

    public Attendance(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = checkAttendanceStatus(dateTime);
    }

    public Attendance(final LocalDateTime dateTime, final AttendanceStatus status) {
        this.dateTime = dateTime;
        this.status = status;
    }

    public AttendanceStatus checkAttendanceStatus(final LocalDateTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return attend(hour, minute, MONDAY_START_HOUR);
        }
        return attend(hour, minute, START_HOUR);
    }

    private AttendanceStatus attend(final int hour, final int minute, final int startHour) {
        // TODO : indent 줄이기
        if (hour >= startHour) {
            if (hour > startHour || minute > ABSENCE_CRITERIA) {
                return AttendanceStatus.LATE_ABSENCE;
            }
            if (minute > LATE_CRITERIA) {
                return AttendanceStatus.LATE;
            }
        }
        return AttendanceStatus.ATTEND;
    }

    public boolean isEqualToDate(final LocalDate today) {
        return today.equals(LocalDate.from(dateTime));
    }

    public AttendanceStatus updateDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = checkAttendanceStatus(dateTime);
        return this.status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}


