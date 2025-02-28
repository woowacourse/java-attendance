package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public enum CampusTime {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    SATURDAY(DayOfWeek.SATURDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    SUNDAY(DayOfWeek.SUNDAY, LocalTime.of(8, 0), LocalTime.of(23, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime openTime;
    private final LocalTime endTime;


    CampusTime(final DayOfWeek dayOfWeek, final LocalTime openTime, final LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.endTime = endTime;
    }

    public static boolean isOpenTime(final LocalDateTime localDateTime) {
        return Arrays.stream(CampusTime.values())
                .filter(campusTime -> Objects.equals(campusTime.dayOfWeek, localDateTime.getDayOfWeek()))
                .anyMatch(campusTime -> !isNotInTime(campusTime, localDateTime.toLocalTime()));
    }

    private static boolean isNotInTime(final CampusTime campusTime, final LocalTime localTime) {
        return localTime.isBefore(campusTime.openTime) || localTime.isAfter(campusTime.endTime);
    }

}
