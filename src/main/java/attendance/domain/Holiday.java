package attendance.domain;

import java.time.LocalDate;

public class Holiday {

    private final LocalDate holiday;

    public Holiday(final LocalDate date) {
        this.holiday = date;
    }
}
