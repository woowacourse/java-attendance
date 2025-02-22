package attendance.domain;

import attendance.constant.Holiday;
import attendance.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {

    private final LocalDateTime attendedTime;

    private Attendance(LocalDateTime attendedAt) {
        validateDayOfWeek(attendedAt);
        validateHoliday(attendedAt);
        this.attendedTime = attendedAt;
    }

    public LocalDateTime getAttendedTime() {
        return attendedTime;
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.compute(attendedTime);
    }

    public static Attendance of(LocalDateTime attendedAt) {
        return new Attendance(attendedAt);
    }

    public static Attendance ofAbsence(LocalDate attendedAt) {
        return new Attendance(attendedAt.atStartOfDay());
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
