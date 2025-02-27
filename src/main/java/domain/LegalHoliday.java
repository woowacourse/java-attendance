package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum LegalHoliday {
    NEW_YEAR(LocalDate.of(2025, 1, 1)),
    LUNAR_NEW_YEAR_1(LocalDate.of(2025, 1, 27)),
    LUNAR_NEW_YEAR_2(LocalDate.of(2025, 1, 28)),
    LUNAR_NEW_YEAR_3(LocalDate.of(2025, 1, 29)),
    LUNAR_NEW_YEAR_4(LocalDate.of(2025, 1, 30)),
    INDEPENDENCE_MOVEMENT_DAY(LocalDate.of(2025, 3, 1)),
    SUBSTITUTE_INDEPENDENCE_DAY(LocalDate.of(2025, 3, 3)),
    CHILDREN_BUDDHA_DAY(LocalDate.of(2025, 5, 5)),
    SUBSTITUTE_CHILDREN_DAY(LocalDate.of(2025, 5, 6)),
    MEMORIAL_DAY(LocalDate.of(2025, 6, 6)),
    LIBERATION_DAY(LocalDate.of(2025, 8, 15)),
    NATIONAL_FOUNDATION_DAY(LocalDate.of(2025, 10, 3)),
    CHUSEOK_1(LocalDate.of(2025, 10, 5)),
    CHUSEOK_2(LocalDate.of(2025, 10, 6)),
    CHUSEOK_3(LocalDate.of(2025, 10, 7)),
    CHUSEOK_4(LocalDate.of(2025, 10, 8)),
    HANGUL_DAY(LocalDate.of(2025, 10, 9)),
    CHRISTMAS(LocalDate.of(2025, 12, 25));

    private final LocalDate date;

    LegalHoliday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(LegalHoliday.values())
                .anyMatch(holiday -> date.isEqual(holiday.date));
    }
}
