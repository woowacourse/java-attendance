package domain.holiday;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import service.date_convertor.LunarDateConvertor;

public class Holidays {
    
    private final LunarDateConvertor lunarDateConvertor;
    private final List<Holiday> holidays;
    
    public Holidays(final LunarDateConvertor lunarDateConvertor, final List<Holiday> holidays) {
        this.lunarDateConvertor = lunarDateConvertor;
        this.holidays = holidays;
    }
    
    public boolean isHoliday(LocalDate date) {
        if (hasSolarHolidayMatch(date)) {
            return true;
        }
        
        final var lunarDate = lunarDateConvertor.convertSolarDateToLunarDate(date);
        return hasLunarHolidayMatch(lunarDate);
    }
    
    private boolean hasSolarHolidayMatch(final LocalDate date) {
        return holidays.stream()
                .filter(holiday -> !holiday.isLunarHoliday())
                .anyMatch(holiday -> isSameDay(date, holiday));
    }
    
    private boolean hasLunarHolidayMatch(final LocalDate lunarDate) {
        return Arrays.stream(KoreanHoliday.values())
                .filter(Holiday::isLunarHoliday)
                .anyMatch(holiday -> isSameDay(lunarDate, holiday));
    }
    
    private boolean isSameDay(final LocalDate date, final Holiday holiday) {
        return LocalDate.of(date.getYear(), holiday.getMonth(), holiday.getDayOfMonth()).equals(date);
    }
}
