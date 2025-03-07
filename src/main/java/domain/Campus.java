package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Campus {
    private static final LocalDate START_DATE_OF_7TH_PERIOD = LocalDate.of(2025, 2, 11);
    private static final Campus INSTANCE = new Campus(START_DATE_OF_7TH_PERIOD);

    private final LocalDate startDate;

    private Campus(LocalDate startDate) {
        this.startDate = startDate;
    }

    public static Campus getInstance() {
        return INSTANCE;
    }

    public int countValidDays(LocalDate lastDate) {
        return (int) startDate.datesUntil(lastDate)
                .filter(this::isOpen)
                .count();
    }

    public boolean isOpen(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() != DayOfWeek.SATURDAY
                && attendanceDate.getDayOfWeek() != DayOfWeek.SUNDAY
                && !LegalHoliday.isHoliday(attendanceDate)
                && !Vacation.isVacation(attendanceDate);
    }

    public List<LocalDate> getOpenDaysUntil(LocalDate lastDate) {
        return startDate.datesUntil(lastDate)
                .filter(this::isOpen)
                .toList();
    }
}
