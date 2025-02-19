package util;

import java.time.DayOfWeek;
import java.util.List;

public class Convertor {

    public static String convertDayOfWeekToKorean(DayOfWeek dayOfWeek) {
        List<String> days = List.of("일", "월", "화", "수", "목", "금", "토");
        return days.get(dayOfWeek.getValue() % 7);
    }
}
