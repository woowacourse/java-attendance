package attendance.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord {
    private final WoowaDate attendanceDate;
    private LocalTime attendanceTime;

    public AttendanceRecord(WoowaDate attendanceDate, LocalTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        EducationTime educationTime = EducationTime.from(attendanceDate.getDayOfWeek());
        LocalTime startTime = educationTime.getStartTime();
        Duration duration = Duration.between(startTime, attendanceTime);
        long minutes = duration.toMinutes();

        return AttendanceStatus.from(minutes);
    }

    public void modify(LocalTime modifyTime) {
        attendanceTime = modifyTime;
    }

    public boolean isSameDate(WoowaDate date) {
        return date.equals(attendanceDate);
    }

    public AttendanceRecord copy() {
        return new AttendanceRecord(this.attendanceDate, this.attendanceTime);
    }

    public LocalDate getDate() {
        return attendanceDate.toLocalDate();
    }

    public LocalDateTime getDateTIme() {
        return LocalDateTime.of(attendanceDate.toLocalDate(), attendanceTime);
    }

}
