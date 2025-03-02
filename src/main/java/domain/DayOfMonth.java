package domain;

import java.time.LocalDate;
import java.util.List;

public record DayOfMonth(int dayOfMonth) {
    public static final List<Integer> HOLIDAYS = List.of(25);
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
    public static final int START_OF_DAY = 1;
    public static final int END_OF_DAY = 31;

    public DayOfMonth {
        isInvalidDayOfMonth(dayOfMonth);
    }

    public boolean isHoliday(LocalDate today) {
        if (HOLIDAYS.contains(dayOfMonth)) {
            return true;
        }
        LocalDate newDay = LocalDate.of(today.getYear(), today.getMonth().getValue(), dayOfMonth);
        return newDay.getDayOfWeek().getValue() == SATURDAY || newDay.getDayOfWeek().getValue() == SUNDAY;
    }

    private void isInvalidDayOfMonth(int dayOfMonth) {
        if (dayOfMonth < START_OF_DAY || dayOfMonth > END_OF_DAY) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }
}
