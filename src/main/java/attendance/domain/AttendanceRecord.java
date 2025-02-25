package attendance.domain;

import java.time.Duration;
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
}
