package domain.attendance;

import java.time.LocalDate;
import java.time.LocalTime;

public class BasicAttendanceDate extends AttendanceDate {
    private final LocalTime time;

    public BasicAttendanceDate(LocalDate date, AttendanceStatus status, LocalTime time) {
        super(date, status);
        this.time = time;
    }
}
