package util;

import java.time.LocalDate;

public class DateGenerator {
    private final LocalDate date;
    
    public DateGenerator(int year, int month, int day) {
        this.date =  LocalDate.of(year, month, day);
    }
    
    public LocalDate getDate() {
        return date;
    }
}
