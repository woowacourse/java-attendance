package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationTimeChecker {

    public boolean isContainsOperationTime(final LocalTime targetTime) {
        return !targetTime.isBefore(LocalTime.of(8, 0)) && !targetTime.isAfter(LocalTime.of(23, 0));
    }

    public LocalTime getEducationStartTime(final LocalDate targetDate) {
        checkIsOperationDate(targetDate);
        if (targetDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }

    public void checkIsOperationDate(final LocalDate targetDate) {
        if (isWeekend(targetDate) || isHoliday(targetDate)) {
            throw new IllegalArgumentException("운영일이 아닙니다.");
        }
    }

    private boolean isWeekend(final LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.SUNDAY || targetDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    private boolean isHoliday(final LocalDate targetDate) {
        return targetDate.isEqual(LocalDate.of(2024, 12, 25));
    }
}
