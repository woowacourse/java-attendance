package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String attendanceStatus;

    public Attendance(LocalDateTime attendanceDateTime) {
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
        this.attendanceStatus = checkAttendanceStatus();
    }

    private String checkAttendanceStatus() {
        if (attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            if (attendanceTime.isBefore(LocalTime.of(13, 5)) || attendanceTime.equals(LocalTime.of(13, 5))) {
                return "출석";
            }
            if (attendanceTime.isBefore(LocalTime.of(13, 30)) || attendanceTime.equals(LocalTime.of(13, 30))) {
                return "지각";
            }
            return "결석";
        }

        if (attendanceTime.isBefore(LocalTime.of(10, 5)) || attendanceTime.equals(LocalTime.of(10, 5))) {
            return "출석";
        }
        if (attendanceTime.isBefore(LocalTime.of(10, 30)) || attendanceTime.equals(LocalTime.of(10, 30))) {
            return "지각";
        }
        return "결석";
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
