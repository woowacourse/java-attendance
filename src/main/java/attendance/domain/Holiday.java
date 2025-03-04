package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate holidayDate;

    Holiday(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public static boolean isHoliday(LocalDate validateDate) {
        return Arrays.stream(Holiday.values())
            .anyMatch(result -> result.isEqualDate(validateDate));
    }

    private boolean isEqualDate(LocalDate findDate) {
        return holidayDate.getMonth() == findDate.getMonth() &&
            holidayDate.getDayOfMonth() == findDate.getDayOfMonth();
    }
}
