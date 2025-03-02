package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Current {
    private static final LocalDate TODAY = LocalDate.of(2024, 12, 11);

    public static LocalDate getToday() {
        return TODAY;
    }

    public static String getStringOfThisMonth() {
        return TODAY.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
