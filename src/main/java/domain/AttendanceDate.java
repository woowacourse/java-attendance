package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDate {

    private final LocalDate localDate;

    public AttendanceDate(final LocalDate localDate) {
        validateDate(localDate);
        this.localDate = localDate;
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

    private void validateDate(final LocalDate localDate) {
        if (CampusHoliday.isDayOff(localDate)) {
            throw new IllegalArgumentException();
        }
    }
}
