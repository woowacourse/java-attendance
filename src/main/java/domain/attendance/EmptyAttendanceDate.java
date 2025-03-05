package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmptyAttendanceDate extends AttendanceDate {
    public EmptyAttendanceDate(LocalDate date, AttendanceStatus status) {
        super(date, status);
    }

    @Override
    public LocalDateTime getDateTime() {
        throw new EmptyAttendanceDateException(this.getDate());
    }
}
