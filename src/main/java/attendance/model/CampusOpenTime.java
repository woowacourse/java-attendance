package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum CampusOpenTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    SATURDAY(DayOfWeek.SATURDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    SUNDAY(DayOfWeek.SUNDAY, LocalTime.of(8, 0), LocalTime.of(23, 0)),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    CampusOpenTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static boolean isDurationTime(LocalDate localDate, LocalTime time) {
        CampusOpenTime durationTime = Arrays.stream(CampusOpenTime.values())
                .filter(woowaDurationTime -> woowaDurationTime.dayOfWeek.equals(localDate.getDayOfWeek()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 날짜입니다."));
        return !(time.isBefore(durationTime.startTime) || time.isAfter(durationTime.endTime));
    }
}
