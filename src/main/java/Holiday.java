import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(Month.DECEMBER, 25),
    ;

    private final Month month;
    private final int day;

    Holiday(Month month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean matches(LocalDate date) {
        return Arrays.stream(values()).anyMatch(holiday -> holiday.isSameDate(date));
    }

    private boolean isSameDate(LocalDate date) {
        return month == date.getMonth() && day == date.getDayOfMonth();
    }
}
