package service.date_convertor;

import java.time.LocalDate;

import com.github.usingsky.calendar.KoreanLunarCalendar;

public class KoreanLunarDateConvertor implements LunarDateConvertor {
    
    @Override
    public LocalDate convertSolarDateToLunarDate(final LocalDate date) {
        KoreanLunarCalendar calendar = KoreanLunarCalendar.getInstance();
        calendar.setSolarDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        
        return LocalDate.of(calendar.getLunarYear(), calendar.getLunarMonth(), calendar.getLunarDay());
    }
}
