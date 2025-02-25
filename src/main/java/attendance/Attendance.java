package attendance;

import java.time.LocalDate;

public class Attendance {
    private final LocalDate date;

    public Attendance(final LocalDate date) {
        this.date = date;
    }

    public boolean isDateEquals(final LocalDate date) {
        return this.date.isEqual(date);
    }
}
