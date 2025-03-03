package model.date;

import model.exception.HolidayAttendanceException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import model.exception.SystemException;

public class December {
    public static final int START_DATE = 1;
    public static final int END_DATE = 31;

    private static final int YEAR = 2024;
    private static final int MONTH = 12;
    private static final int CHRISTMAS = 25;

    public static void validateHoliday(LocalDate date) {
        if (isHolidayAt(date)) {
            throw new HolidayAttendanceException(date);
        }
    }

    public static boolean isHolidayAt(LocalDate date) {
        if (date.getYear() != YEAR || date.getMonthValue() != MONTH) {
            throw new SystemException();
        }
        return date.getDayOfMonth() == CHRISTMAS
                || date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static LocalDate createDecemberDateWith(int rawDate) {
        return LocalDate.of(YEAR, MONTH, rawDate);
    }

    public static LocalDate now() {
        return LocalDate.of(YEAR, MONTH, 13);
    }
}
