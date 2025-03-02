package domain.attendance;

import java.time.LocalDate;

public class EmptyAttendanceDate extends AttendanceDate {
    public EmptyAttendanceDate(LocalDate date, AttendanceStatus status) {
        super(date, status);
    }
}
