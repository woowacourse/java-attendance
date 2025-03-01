package attendance.domain;

import java.util.Arrays;

public enum Holiday {

    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holiday(final int month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(final int monthInput, int dayInput) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.month == monthInput && holiday.day == dayInput);
    }
}
