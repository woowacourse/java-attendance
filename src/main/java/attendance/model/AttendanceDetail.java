package attendance.model;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDetail {

    private LocalDateTime localDateTime;
    private Attendance attendance;

    public AttendanceDetail(LocalDateTime localDateTime) {
        validateWeekendOrHoliday(localDateTime);
        this.localDateTime = localDateTime;
        this.attendance = Attendance.from(localDateTime);
    }

    private void validateWeekendOrHoliday(LocalDateTime localDateTime) {
        if (CustomLocalDateTime.isWeekendOrHoliday(localDateTime.toLocalDate())) {
            throw new IllegalArgumentException(localDateTime.format(NOT_ATTENDABLE_FORMATTER));
        }
    }

    public void modify(LocalTime localTime) {
        localDateTime = LocalDateTime.of(
                localDateTime.toLocalDate(),
                localTime
        );
        this.attendance = Attendance.from(localDateTime);
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public LocalDate getAttendanceDate() {
        return localDateTime.toLocalDate();
    }

    public LocalDateTime getAttendanceDateTime() {
        return localDateTime;
    }

    public boolean isSameAs(Attendance attendance) {
        return attendance == this.attendance;
    }

}
