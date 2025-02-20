package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import util.Constants;

public enum Week {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0));

    private final LocalTime attendanceTime;

    private static Map<DayOfWeek, String> initWeekNames() {
        final Map<DayOfWeek, String> weekNames = new HashMap<>();
        weekNames.put(DayOfWeek.MONDAY, "월요일");
        weekNames.put(DayOfWeek.TUESDAY, "화요일");
        weekNames.put(DayOfWeek.WEDNESDAY, "수요일");
        weekNames.put(DayOfWeek.THURSDAY, "목요일");
        weekNames.put(DayOfWeek.FRIDAY, "금요일");
        weekNames.put(DayOfWeek.SATURDAY, "토요일");
        weekNames.put(DayOfWeek.SUNDAY, "일요일");

        return weekNames;
    }

    Week(final LocalTime localTime) {
        this.attendanceTime = localTime;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public static Week findByAttendanceTime(final LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();
        final String dayOfWeek = Week.findKoreanName(localDateTime.getDayOfWeek());

        return Arrays.stream(Week.values())
                .filter(week -> week.name().equals(day.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d월 %d일 %s은 등교일이 아닙니다.", Constants.MONTH,
                                localDateTime.getDayOfMonth(), dayOfWeek)));
    }

    public static String findKoreanName(final DayOfWeek day) {
        return initWeekNames().get(day);
    }
}
