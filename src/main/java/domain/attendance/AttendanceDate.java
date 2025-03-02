package domain.attendance;

import java.time.LocalDate;

public abstract class AttendanceDate {
    private final LocalDate date;
    private final AttendanceStatus status;

    public AttendanceDate(LocalDate date, AttendanceStatus status) {
        this.date = date;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
