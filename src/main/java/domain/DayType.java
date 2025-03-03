package domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import util.OutputParser;

public enum DayType {
    MONDAYS(List.of(2, 9, 16, 23, 30)),
    SATURDAYS(List.of(7, 13, 21, 28)),
    SUNDAYS(List.of(1, 8, 15, 22, 29)),
    HOLIDAYS(List.of(25)),
    WORKING_DAYS(List.of());

    private final List<Integer> days;

    DayType(List<Integer> days) {
        this.days = days;
    }

    public static boolean matches(LocalDate date, DayType dayType) {
        return findByDate(date).equals(dayType);
    }

    private static DayType findByDate(LocalDate date) {
        int day = date.getDayOfMonth();

        return Arrays.stream(DayType.values())
                .filter(dayType -> dayType.containsDay(day))
                .findAny()
                .orElse(WORKING_DAYS);
    }

    public static void validateIsWorkingDay(LocalDate date) {
        if (!checkIsWorkingDay(date)) {
            throw new IllegalArgumentException(
                    ErrorCode.DATE_NOT_ATTENDING_DATE.getFormattedMessage(OutputParser.parseDateInKorean(date)));
        }
    }

    public static boolean checkIsWorkingDay(LocalDate date) {
        return findByDate(date) == WORKING_DAYS;
    }

    private boolean containsDay(int value) {
        return days.contains(value);
    }
}
