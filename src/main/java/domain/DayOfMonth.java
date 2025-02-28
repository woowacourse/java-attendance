package domain;

import java.time.LocalDate;
import java.util.List;

public record DayOfMonth(int dayOfMonth) {

    public DayOfMonth {
        isInvalidDayOfMonth(dayOfMonth);
    }

    public boolean isHoliday(List<Integer> holidays, LocalDate today) {
        if (holidays.contains(dayOfMonth)) {
            return true;
        }
        LocalDate newDay = LocalDate.of(today.getYear(), today.getMonth().getValue(), dayOfMonth);
        return newDay.getDayOfWeek().getValue() == 6 || newDay.getDayOfWeek().getValue() == 7;
    }

    private void isInvalidDayOfMonth(int dayOfMonth) {
        if (dayOfMonth <= 0 || dayOfMonth > 31) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }
}
