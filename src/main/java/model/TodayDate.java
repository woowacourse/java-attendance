package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import util.LocalDateTimePrintFormatter;

public class TodayDate {

    private final LocalDate todayDate;

    public TodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public LocalDateTime getTodayDateTIme() {
        return todayDate.atTime(0,0);
    }

    public String getTodayDay() {
        DayOfWeek dayOfWeek = todayDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public void isHoliday() {
        if (AttendanceCalculator.checkHoliday(getTodayDateTIme())) {
            throw new IllegalArgumentException(LocalDateTimePrintFormatter.isNotAttendanceAvailable(todayDate));
        }
    }
}
