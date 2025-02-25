package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final LocalDateTime attendanceDateTime;
    private final String attendanceStatus;

    public Attendance(LocalDateTime attendacneTime) {
        this.attendanceDateTime = attendacneTime;
        this.attendanceStatus = checkAttendanceStatus();
    }

    private String checkAttendanceStatus() {
        if (attendanceDateTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            if (attendanceDateTime.toLocalTime().isBefore(LocalTime.of(13, 5)) || attendanceDateTime.toLocalTime()
                    .equals(LocalTime.of(13, 5))) {
                return "출석";
            }
            if (attendanceDateTime.toLocalTime().isBefore(LocalTime.of(13, 30)) || attendanceDateTime.toLocalTime()
                    .equals(LocalTime.of(13, 30))) {
                return "지각";
            }
            return "결석";
        }

        if (attendanceDateTime.toLocalTime().isBefore(LocalTime.of(10, 5)) || attendanceDateTime.toLocalTime()
                .equals(LocalTime.of(10, 5))) {
            return "출석";
        }
        if (attendanceDateTime.toLocalTime().isBefore(LocalTime.of(10, 30)) || attendanceDateTime.toLocalTime()
                .equals(LocalTime.of(10, 30))) {
            return "지각";
        }
        return "결석";
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
