package domain.date;

import java.time.LocalDateTime;

public class CustomDate {
    public static final int YEAR = 2024;
    public static final Month MONTH = Month.DECEMBER;
    private static final int FIXED_DATE = 13;

    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        return now.withYear(YEAR).withMonth(MONTH.getValue()).withDayOfMonth(FIXED_DATE);
    }
}
