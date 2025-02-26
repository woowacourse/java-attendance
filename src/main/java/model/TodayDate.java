package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class TodayDate {

    private final LocalDate todayDate;

    public TodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public LocalDateTime getTodayDateTIme() {
        return todayDate.atTime(0, 0);
    }

    public String getTodayDay() {
        DayOfWeek dayOfWeek = todayDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public boolean isHoliday() {
        return  (AttendanceCalculator.checkHoliday(getTodayDateTIme()));
    }
}
