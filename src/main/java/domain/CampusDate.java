package domain;

import java.time.LocalDate;

public class CampusDate {

    private final LocalDate date;

    private CampusDate(LocalDate date) {
        this.date = date;
    }

    public static CampusDate ofNowAndDay(LocalDate now, int day) {
        return new CampusDate(now.withDayOfMonth(day));
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }
}
