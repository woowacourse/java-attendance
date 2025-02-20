package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
