package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum DayOfWeek {
    MONDAY(1, "월요일", LocalTime.of(13, 0)),
    TUESDAY(2, "화요일", LocalTime.of(10, 0)),
    WEDNESDAY(3, "수요일", LocalTime.of(10, 0)),
    THURSDAY(4, "목요일", LocalTime.of(10, 0)),
    FRIDAY(5, "금요일", LocalTime.of(10, 0)),
    SATURDAY(6, "토요일", null),
    SUNDAY(7, "일요일", null);

    private final Integer id;
    private final String name;
    private final LocalTime standardTime;

    DayOfWeek(Integer id, String name, LocalTime standardTime) {
        this.id = id;
        this.name = name;
        this.standardTime = standardTime;
    }

    public static String getNameById(Integer id) {
        return Arrays.stream(values())
                .filter(value -> value.id.equals(id))
                .map(value -> value.name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID에 대한 요일이 없습니다."));
    }

    public static LocalTime getStandardTimeById(Integer id) {
        return Arrays.stream(values())
                .filter(value -> value.id.equals(id))
                .map(value -> value.standardTime)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID에 대한 요일이 없습니다."));
    }

    public static DayOfWeek getInstance(LocalDate date) {
        return Arrays.stream(values())
                .filter(value -> value.id.equals(date.getDayOfWeek().getValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 요일이 없습니다."));
    }

    public LocalTime getStandardTime() {
        return standardTime;
    }

}
