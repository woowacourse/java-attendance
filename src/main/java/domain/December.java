package domain;

import java.util.Arrays;
import java.util.List;

public enum December {
    MONDAY(List.of(2, 9, 16, 23, 30), "월요일"),
    TUESDAY(List.of(3, 10, 17, 24, 31), "화요일"),
    WEDNESDAY(List.of(4, 11, 18), "수요일"),
    THURSDAY(List.of(5, 12, 19, 26), "목요일"),
    FRIDAY(List.of(6, 13, 20, 27), "금요일"),
    SATURDAY(List.of(7, 14, 21, 28), "토요일"),
    SUNDAY(List.of(8, 15, 22, 29), "일요일"),
    HOLIDAY(List.of(25), "휴일");
    private List<Integer> dates;
    private String dayOfWeek;

    December(List<Integer> dates, String dayOfWeek) {
        this.dates = dates;
        this.dayOfWeek = dayOfWeek;
    }

    public static December findDayOfWeek(int dayOfMonth) {
        for(December day:December.values()){
            if(day.dates.contains(dayOfMonth)){
                return day;
            }
        }
        throw new IllegalArgumentException();
    }

    public static List<Integer> getWeekDays() {
        return Arrays.stream(December.values()).filter(a -> !List.of(SUNDAY, SATURDAY, HOLIDAY).contains(a)).flatMap(a -> a.dates.stream()).sorted().toList();
    }

    public static String getDayByDate(int dayOfMonth) {
        for (December day : December.values()) {
            if (day.dates.contains(dayOfMonth)) {
                return day.dayOfWeek;
            }
        }
        return null;
    }
}

