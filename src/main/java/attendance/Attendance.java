package attendance;

import java.time.LocalDate;

public class Attendance {
    private final LocalDate date;
    private final String status;

    public Attendance(final LocalDate date, final String status) {
        this.date = date;
        this.status = status;
    }

    public boolean isDateEquals(final LocalDate date) {
        return this.date.isEqual(date);
    }

    public String getStatus() {
        return status;
    }
}
