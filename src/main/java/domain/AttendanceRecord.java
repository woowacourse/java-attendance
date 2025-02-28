package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord implements Comparable<AttendanceRecord> {
    private final LocalDateTime dateTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceRecord(LocalDateTime dateTime) {
        validate(dateTime);
        this.dateTime = dateTime;
        this.attendanceStatus = AttendanceStatus.getStatus(dateTime.getDayOfWeek(), dateTime.toLocalTime());
    }

    private void validate(LocalDateTime dateTime) {
        if (ClassSchedule.isDayOff(dateTime.toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 등교일이 아닙니다.");
        }
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return this.attendanceStatus;
    }

    @Override
    public int compareTo(AttendanceRecord o) {
        return o.dateTime.toLocalDate().compareTo(this.dateTime.toLocalDate());
    }
}
