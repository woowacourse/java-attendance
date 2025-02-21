package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private LocalDateTime attendanceDateTime;
    private AttendanceStatus status;

    private Attendance(LocalDateTime attendanceDateTime) {
        validateDayOfWeek(attendanceDateTime);
        validateHoliday(attendanceDateTime);
        setStatus(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
    }

    public static Attendance of(LocalDateTime attendanceDateTime) {
        return new Attendance(attendanceDateTime);
    }

    private void validateDayOfWeek(LocalDateTime attendanceDateTime) {
        if (DateUtil.isWeekend(attendanceDateTime)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateHoliday(LocalDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.toLocalDate();
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException();
        }
    }

    private void setStatus(LocalDateTime attendanceDateTime) {
        LocalTime time = attendanceDateTime.toLocalTime();

        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            determineStatus(time, 13);
            return;
        }

        determineStatus(time, 10);
    }

    private void determineStatus(LocalTime time, int hour) {
        if ((time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(hour, 5)))
            || time.equals(LocalTime.of(hour, 5))) {
            status = AttendanceStatus.CHECKIN;
            return;
        }
        if (time.isAfter(LocalTime.of(hour, 5)) && time.isBefore(LocalTime.of(hour, 30))
            || time.equals(LocalTime.of(hour, 30))) {
            status = AttendanceStatus.LATE;
            return;
        }
        status = AttendanceStatus.ABSENCE;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void modify(LocalDateTime modifiedDateTime) {
        setStatus(modifiedDateTime);
        this.attendanceDateTime = modifiedDateTime;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }
}
