package domain;

import com.github.usingsky.calendar.KoreanLunarCalendar;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    
    새해첫날(1, 1, false),
    설날(1, 1, true),
    삼일절(3, 1, false),
    부처님오신날(4, 8, true),
    어린이날(5, 5, false),
    현충일(6, 6, false),
    광복절(8, 15, false),
    추석(8, 15, true),
    개천절(10, 3, false),
    한글날(10, 9, false),
    기독탄신일(12, 25, false),
    ;
    
    private final int month;
    private final int dayOfMonth;
    private final boolean isLunarDate;
    
    Holiday(final int month, final int dayOfMonth, final boolean isLunarDate) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.isLunarDate = isLunarDate;
    }
    
    public static boolean isHoliday(LocalDate date) {
        if (hasSolarHolidayMatch(date)) {
            return true;
        }
        
        final var lunarDate = convertSolarDateToLunarDate(date);
        return hasLunarHolidayMatch(lunarDate);
    }
    
    private static LocalDate convertSolarDateToLunarDate(LocalDate date) {
        KoreanLunarCalendar calendar = KoreanLunarCalendar.getInstance();
        calendar.setSolarDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        
        return LocalDate.of(calendar.getLunarYear(), calendar.getLunarMonth(), calendar.getLunarDay());
    }
    
    private static boolean hasSolarHolidayMatch(final LocalDate date) {
        return Arrays.stream(Holiday.values())
                .filter(holiday -> !holiday.isLunarDate)
                .anyMatch(holiday -> isSameDay(date, holiday));
    }
    
    private static boolean hasLunarHolidayMatch(final LocalDate lunarDate) {
        return Arrays.stream(Holiday.values())
                .filter(holiday -> holiday.isLunarDate)
                .anyMatch(holiday -> isSameDay(lunarDate, holiday));
    }
    
    private static boolean isSameDay(final LocalDate date, final Holiday holiday) {
        return LocalDate.of(date.getYear(), holiday.month, holiday.dayOfMonth).equals(date);
    }
}
