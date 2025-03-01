package util;

import domain.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayConverter {

    public static String getKoreanDayOfWeek(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static LocalDateTime combineTimeAndDate(Time time, LocalDate today) {
        return LocalDateTime.of(today, time.convertTime());
    }
}
