package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import domain.holiday.Holidays;
import domain.holiday.KoreanHoliday;
import service.date_convertor.KoreanLunarDateConvertor;

public class AttendanceDate {
    
    private static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final Holidays holidays = new Holidays(
            new KoreanLunarDateConvertor(),
            List.of(KoreanHoliday.values())
    );
    
    private final LocalDate attendDate;
    
    
    public AttendanceDate(final LocalDate attendDate) {
        validateNotWeekend(attendDate);
        validateNotHoliday(attendDate);
        this.attendDate = attendDate;
    }
    
    private void validateNotWeekend(final LocalDate date) {
        if (isWeekend(date)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }
    
    private static boolean isWeekend(final LocalDate date) {
        return WEEKENDS.contains(date.getDayOfWeek());
    }
    
    private void validateNotHoliday(final LocalDate date) {
        if (holidays.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }
    
    public LocalDate getAttendDate() {
        return attendDate;
    }
    
    public static boolean isAttendDay(final LocalDate date) {
        return !isWeekend(date) && !holidays.isHoliday(date);
    }
}
