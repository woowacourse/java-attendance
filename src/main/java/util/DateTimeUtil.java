package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static boolean isInRange(LocalTime startTime, LocalTime endTime, LocalTime targetTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 앞이어야 합니다.");
        }

        return (targetTime.equals(startTime) || targetTime.isAfter(startTime))
                && (targetTime.equals(endTime) || targetTime.isBefore(endTime));
    }
}
