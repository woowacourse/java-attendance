package attendance.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import attendance.util.TimeFormatter;

public class Campus {

    private static final int CHRISTMAS_DAY = 25;
    private static final int OPEN_HOUR = 8;
    private static final int CLOSE_HOUR = 23;

    public void validateOperationTime(final LocalDateTime localDateTime) {
        LocalTime time = LocalTime.from(localDateTime);
        LocalTime openTime = LocalTime.of(OPEN_HOUR, 0);
        LocalTime closeTime = LocalTime.of(CLOSE_HOUR, 0);
        if (isNotOperationTime(time, closeTime, openTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public void validateOperationDate(final LocalDate date) {
        if (isNotOperationDate(date)) {
            throw new IllegalArgumentException("[ERROR] " + TimeFormatter.formatDate(date) + "은 등교일이 아닙니다.");
        }
    }

    public boolean isNotOperationDate(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return isWeekend(dayOfWeek) || isHoliday(date);
    }

    private static boolean isNotOperationTime(LocalTime time, LocalTime closeTime, LocalTime openTime) {
        return time.isAfter(closeTime) || time.isBefore(openTime);
    }

    private boolean isWeekend(final DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(final LocalDate date) {
        return date.getDayOfMonth() == CHRISTMAS_DAY;
    }
}
