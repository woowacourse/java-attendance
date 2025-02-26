package attendance.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord {
    private LocalDateTime attendanceDateTime;

    public AttendanceRecord(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        EducationTime educationTime = EducationTime.from(attendanceDateTime.getDayOfWeek());
        LocalTime startTime = educationTime.getStartTime();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        Duration duration = Duration.between(startTime, attendanceTime);
        long minutes = duration.toMinutes();

        return AttendanceStatus.from(minutes);
    }

    public void modify(LocalTime modifyTime) {
        attendanceDateTime = LocalDateTime.of(attendanceDateTime.toLocalDate(), modifyTime);
    }

    public boolean isSameDate(LocalDate date) {
        return date.equals(attendanceDateTime.toLocalDate());
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public AttendanceRecord copy() {
        return new AttendanceRecord(this.getAttendanceDateTime());
    }
}
