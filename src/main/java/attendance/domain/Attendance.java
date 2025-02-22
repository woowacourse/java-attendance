package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {

    private LocalDateTime attendedTime;
    private AttendanceStatus status;

    private Attendance(LocalDateTime attendedAt) {
        validateDayOfWeek(attendedAt);
        validateHoliday(attendedAt);
        this.attendedTime = attendedAt;
        this.status = AttendanceStatus.compute(attendedAt);
    }

    public static Attendance of(LocalDateTime attendedAt) {
        return new Attendance(attendedAt);
    }

    public static Attendance ofAbsence(LocalDate attendedAt) {
        return new Attendance(attendedAt.atStartOfDay());
    }

    public void modify(LocalDateTime modifiedDateTime) {
        this.attendedTime = modifiedDateTime;
        this.status = AttendanceStatus.compute(modifiedDateTime);
    }

    public LocalDateTime getAttendedTime() {
        return attendedTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    private void validateDayOfWeek(LocalDateTime attendedAt) {
        if (DateUtil.isWeekend(attendedAt)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateHoliday(LocalDateTime attendedAt) {
        LocalDate date = attendedAt.toLocalDate();
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException();
        }
    }
}
