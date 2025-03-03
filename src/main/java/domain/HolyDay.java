package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum HolyDay {

    CHRISTMAS(LocalDate.of(2024, 12, 25)),
    ;

    private final LocalDate localDate;

    HolyDay(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static boolean isHolyDay(LocalDate localDate) {
        return Arrays.stream(values())
                .anyMatch(holyday -> holyday.localDate.equals(localDate));
    }

}
