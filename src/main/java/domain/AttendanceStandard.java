package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceStandard {
    MONDAY(DayOfWeek.MONDAY, "월요일", LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, "화요일", LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, "수요일", LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, "목요일", LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, "금요일", LocalTime.of(10, 0)),
    SATURDAY(DayOfWeek.SATURDAY, "토요일", null),
    SUNDAY(DayOfWeek.SUNDAY, "일요일", null);

    private final DayOfWeek dayOfWeek;
    private final String name;
    private final LocalTime standardTime;

    AttendanceStandard(DayOfWeek dayOfWeek, String name, LocalTime standardTime) {
        this.dayOfWeek = dayOfWeek;
        this.name = name;
        this.standardTime = standardTime;
    }

    public static String getNameByDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(value -> value.dayOfWeek == dayOfWeek)
                .map(value -> value.name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID에 대한 요일이 없습니다."));
    }

    public static AttendanceStandard getInstance(LocalDate date) {
        return Arrays.stream(values())
                .filter(value -> value.dayOfWeek == date.getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 요일이 없습니다."));
    }

    public LocalTime getStandardTime() {
        return standardTime;
    }

}
