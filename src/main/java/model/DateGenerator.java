package model;

import java.time.LocalDate;

public class DateGenerator {
    public static LocalDate create(int rawDate) {
        return LocalDate.of(2024, 12, rawDate);
    }
}
