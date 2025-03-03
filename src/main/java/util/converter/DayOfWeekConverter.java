package util.converter;

import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekConverter {

    private DayOfWeekConverter() {
    }

    public static String convertDayOfWeekToKorean(DayOfWeek dayOfWeek) {
        List<String> koreanDayOfWeek = List.of("월", "화", "수", "목", "금", "토", "일");
        return koreanDayOfWeek.get(dayOfWeek.ordinal() % 7);
    }
}
