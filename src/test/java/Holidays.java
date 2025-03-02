import java.time.LocalDate;
import java.util.Arrays;

public enum Holidays {

    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holidays(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate today) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.month == today.getMonthValue() && holiday.day == today.getDayOfMonth());
    }
}
