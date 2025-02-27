package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttendanceDateTime implements SystemDateTime {
    private static final LocalDate START_CALENDER = LocalDate.of(2024, Month.DECEMBER, 1);
    private static final LocalDateTime NOW_DATETIME
        = LocalDateTime.of(2024, Month.DECEMBER, 25, 10, 4);
    
    private static final List<Integer> DAT_OF_HOLIDAY = List.of(25);

    @Override
    public LocalDateTime now() {
        return NOW_DATETIME;
    }

    @Override
    public List<LocalDate> extractWorkingDays() {
        return Stream.iterate(START_CALENDER, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(START_CALENDER, nowDatePlus()))
            .filter(this::isWorkingDay)
            .collect(Collectors.toList());
    }

    private static LocalDate nowDatePlus() {
        return NOW_DATETIME.toLocalDate().plusDays(1);
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private static boolean isHoliday(LocalDate date) {
        return DAT_OF_HOLIDAY.stream()
            .anyMatch(day -> date.getDayOfMonth() == day);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue();
    }

}
