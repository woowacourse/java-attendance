package domain;

import java.time.LocalDateTime;
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

    public static final int DEFAULT_YEAR = 2024;
    public static final int DEFAULT_MONTH = 12;

    private final List<Integer> dates;
    private final String dayOfWeek;

    December(List<Integer> dates, String dayOfWeek) {
        this.dates = dates;
        this.dayOfWeek = dayOfWeek;
    }

    public static December findDayOfWeek(int dayOfMonth) {
        return Arrays.stream(values())
                .filter(day -> contains(dayOfMonth, day))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<Integer> getWeekDays() {
        return Arrays.stream(December.values())
                .filter(a -> !List.of(SUNDAY, SATURDAY, HOLIDAY).contains(a))
                .flatMap(a -> a.dates.stream()).sorted().toList();
    }

    public static String getDayByDate(int dayOfMonth) {
        return Arrays.stream(values())
                .filter(a -> contains(dayOfMonth, a))
                .findFirst()
                .map(a -> a.dayOfWeek)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static boolean contains(final int dayOfMonth, final December day) {
        return day.dates.contains(dayOfMonth);
    }

    public static void checkWeekday(LocalDateTime attendTime) {
        if (attendTime.getYear() == DEFAULT_YEAR && attendTime.getMonthValue() == DEFAULT_MONTH) {
            if (December.getWeekDays().contains(attendTime.getDayOfMonth())) {
                return;
            }
        }
        throw new IllegalArgumentException(
                String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                        attendTime.getMonthValue(),
                        attendTime.getDayOfMonth(),
                        December.getDayByDate(attendTime.getDayOfMonth())));
    }
}

