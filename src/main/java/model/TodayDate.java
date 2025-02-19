package model;

import java.time.LocalDate;
public class TodayDate {

    private final LocalDate todayDate;

    public TodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }
}
