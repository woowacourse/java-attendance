package util;

import domain.DayOfMonth;
import domain.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayConverter {

    public static String getKoreanDayOfWeek(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static LocalDateTime combineTimeAndDate(Time time, LocalDate today) {
        return LocalDateTime.of(today, time.convertTime());
    }

    public static LocalDate combineDayAndDate(DayOfMonth dayOfMonth, LocalDate today) {
        return LocalDate.of(today.getYear(), today.getMonth().getValue(), dayOfMonth.dayOfMonth());
    }

    public static List<LocalDate> getUntilToday(LocalDate today) {
        return IntStream.range(1, today.getDayOfMonth())
                .mapToObj(day -> LocalDate.of(today.getYear(), today.getMonthValue(), day))
                .collect(Collectors.toList());
    }
}
