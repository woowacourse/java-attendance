package attendance.model.campus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CampusOperationPolicy {

    private static final LocalTime DEFAULT_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_CLOSE_TIME = LocalTime.of(23, 0);

    public boolean isCampusOpen(final LocalDateTime dateTime) {
        if (isWeekEnd(dateTime.toLocalDate())) {
            return false;
        }

        final LocalTime time = dateTime.toLocalTime();

        return time.isAfter(DEFAULT_OPEN_TIME.minusMinutes(1))
                && time.isBefore(DEFAULT_CLOSE_TIME.plusMinutes(1));
    }

    private boolean isWeekEnd(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();

        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
