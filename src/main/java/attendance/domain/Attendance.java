package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {

    private LocalDateTime attendanceDateTime;
    private AttendanceStatus status;

    private Attendance(LocalDateTime attendanceDateTime) {
        validateDayOfWeek(attendanceDateTime);
        validateHoliday(attendanceDateTime);
        this.status = AttendanceStatus.determineStatus(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
    }

    public static Attendance of(LocalDateTime attendanceDateTime) {
        return new Attendance(attendanceDateTime);
    }

    private void validateDayOfWeek(LocalDateTime attendanceDateTime) {
        if (DateUtil.isWeekend(attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException();
        }
    }

    private void validateHoliday(LocalDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.toLocalDate();
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void modify(LocalDateTime modifiedDateTime) {
        this.status = AttendanceStatus.determineStatus(modifiedDateTime);
        this.attendanceDateTime = modifiedDateTime;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }
}
