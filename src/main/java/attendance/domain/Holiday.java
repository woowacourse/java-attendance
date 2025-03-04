package attendance.domain;

import java.time.LocalDate;

public class Holiday {
    private final String name;
    private final int month;
    private final int day;

    public Holiday(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public boolean isMatch(LocalDate date) {
        return date.getMonthValue() == month && date.getDayOfMonth() == day;
    }
}
