package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;
    private final String status;

    public Attendance(final LocalDate date, final LocalTime time, final String status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public boolean isDateEquals(final LocalDate date) {
        return this.date.isEqual(date);
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
