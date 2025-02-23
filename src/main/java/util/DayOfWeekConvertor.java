package util;

import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekConvertor {

    private static final List<String> days = List.of("일", "월", "화", "수", "목", "금", "토");

    public static String convertDayOfWeekToKorean(DayOfWeek dayOfWeek) {
        return days.get(dayOfWeek.getValue() % 7);
    }
}
