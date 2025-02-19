package domain.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtil {
    public static LocalDateTime TODAY = LocalDateTime.of(LocalDate.of(2024, 12, 07), LocalTime.of(10, 30, 00));

    public static LocalDate getFirstDateOfMonth() {
        return TODAY.toLocalDate()
                .atStartOfDay()
                .toLocalDate();
    }

    public static boolean isWeekend(LocalDate localDate) {
        //TODO: 공휴일 체크
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static LocalDateTime assembleDateAndTime(LocalDate localDate, LocalTime localTime) {
        return LocalDateTime.of(localDate, localTime);
    }
}
