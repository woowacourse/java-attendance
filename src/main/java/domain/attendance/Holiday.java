package domain.attendance;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private int month;
    private int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate attendTime) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.getMonth() == attendTime.getMonth().getValue()
                        && holiday.getDay() == attendTime.getDayOfMonth());
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
