package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class TodayDate {

    private final LocalDate todayDate;

    public TodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public String getTodayDayName() {
        DayOfWeek dayOfWeek = todayDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public boolean isHoliday() {
        return this.toAttendanceDateTime().isChristmas() || this.toAttendanceDateTime().isWeekend();
    }

    public AttendanceDateTime toAttendanceDateTime() {
        return new AttendanceDateTime(todayDate.atTime(0, 0));
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

}
