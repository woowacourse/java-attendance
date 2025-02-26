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
        validateAttendanceDate(attendanceDateTime.toLocalDate());
        validateAttendanceTime(attendanceDateTime.toLocalTime());
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
        this.attendanceStatus = determineAttendanceStatus();
    }


    private void validateAttendanceDate(LocalDate localDate) {

        throw new IllegalArgumentException("주말 및 공휴일은 출석을 받지");
    }
    private void validateAttendanceTime(LocalTime localTime) {
        if (localTime.isBefore(LocalTime.of(8, 0)) || localTime.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("[ERROR] 지정된 시간이 아니면 등교가 불가능합니다.");
        }

    }

    private String determineAttendanceStatus() {
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
