package util;

import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekConvertor {

    private static final List<String> DAY_OF_WEEK_KOREAN = List.of("월", "화", "수", "목", "금", "토", "일");

    public static String convertToKorean(DayOfWeek dayOfWeek) {
        return DAY_OF_WEEK_KOREAN.get(dayOfWeek.getValue() - 1);
    }
}
