package attendance.constant;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    CHRISTMAS_2024(LocalDate.of(2024, 12, 25)),
    NEW_YEAR_DAY(LocalDate.of(2025, 1, 1)),
    LUNAR_NEW_YEAR_ALTERNATIVE_DAY(LocalDate.of(2025, 1, 27)),
    LUNAR_NEW_YEAR_DAY1(LocalDate.of(2025, 1, 28)),
    LUNAR_NEW_YEAR_DAY(LocalDate.of(2025, 1, 29)),
    LUNAR_NEW_YEAR_DAY2(LocalDate.of(2025, 1, 30)),
    INDEPENDENCE_MOVEMENT_DAY(LocalDate.of(2025, 3, 1)),
    INDEPENDENCE_MOVEMENT_ALTERNATIVE_DAY(LocalDate.of(2025, 3, 3)),
    CHILDRENS_DAY(LocalDate.of(2025, 5, 5)),
    BUDDHAS_ALTERNATIVE_DAY(LocalDate.of(2025, 5, 6)),
    MEMORIAL_DAY(LocalDate.of(2025, 6, 6)),
    LIBERATION_DAY(LocalDate.of(2025, 8, 5)),
    NATIONAL_FOUNDATION_DAY(LocalDate.of(2025, 10, 3)),
    CHUSEOK_1(LocalDate.of(2025, 10, 5)),
    CHUSEOK(LocalDate.of(2025, 10, 6)),
    CHUSEOK_2(LocalDate.of(2025, 10, 7)),
    CHUSEOK_ALTERNATIVE_DAY(LocalDate.of(2025, 10, 8)),
    HANGEUL_DAY(LocalDate.of(2025, 10, 9)),
    CHRISTMAS_2025(LocalDate.of(2025, 12, 25))
    ;

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate inputDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.date.isEqual(inputDate));
    }
}
