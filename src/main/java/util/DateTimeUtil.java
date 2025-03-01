package util;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
            Locale.KOREAN);
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일",
            Locale.KOREAN);
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static LocalDate nowDate() {
        return LocalDate.of(2025, 2, 28);
//        return LocalDate.now();
    }

    public static LocalTime convertToLocalTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(time + ": 올바르지 않은 시간 형식입니다.");
        }
    }

    public static LocalDate convertDay(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(day + ": 이 달에 존재하지 않는 날짜(일)입니다.");
        }
    }

    public static boolean isInRange(LocalTime startTime, LocalTime endTime, LocalTime targetTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 앞이어야 합니다.");
        }

        return (targetTime.equals(startTime) || targetTime.isAfter(startTime))
                && (targetTime.equals(endTime) || targetTime.isBefore(endTime));
    }
}
