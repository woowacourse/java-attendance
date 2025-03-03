package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Campus {
    private static final LocalDate START_DATE = LocalDate.of(2025, 2, 11);

    public static int countValidDays(LocalDate lastDate) {
        return (int) START_DATE.datesUntil(lastDate)
                .filter(Campus::isOpen)
                .count();
    }

    public static boolean isOpen(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() != DayOfWeek.SATURDAY
                && attendanceDate.getDayOfWeek() != DayOfWeek.SUNDAY
                && !LegalHoliday.isHoliday(attendanceDate)
                && !Vacation.isVacation(attendanceDate);
    }

    public static List<LocalDate> getOpenDaysUntil(LocalDate lastDate) {
        return START_DATE.datesUntil(lastDate)
                .filter(Campus::isOpen)
                .toList();
    }
}
