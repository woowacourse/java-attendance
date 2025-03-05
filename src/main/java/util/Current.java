package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Current {
    private static final LocalDate TODAY = LocalDate.of(2024, 12, 11);

    public static LocalDate getToday() {
        return TODAY;
    }

    public static int getDayOfToday() {
        return TODAY.getDayOfMonth();
    }

    public static int getDayOfYesterday() {
        return TODAY.minusDays(1)
                .getDayOfMonth();
    }

    public static int getMonthOfToday() {
        return TODAY.getMonthValue();
    }

    public static int getYearOfToday() {
        return TODAY.getYear();
    }

    public static String getStringOfThisMonth() {
        return TODAY.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
