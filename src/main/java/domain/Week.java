package domain;

import error.CustomIllegalArgumentException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

public enum Week {

    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0));

    public static final DateTimeFormatter KOREAN_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 dd일 HH:mm EEEE",
            Locale.KOREAN);
    public static final DateTimeFormatter NON_SCHOOL_DAY_FORMAT = DateTimeFormatter.ofPattern("M월 dd일 EEEE",
            Locale.KOREAN);
    public static final DateTimeFormatter ABSENCE_FORMAT = DateTimeFormatter.ofPattern("M월 dd일 --:-- EEEE",
            Locale.KOREAN);

    private final LocalTime attendanceTime;

    Week(final LocalTime localTime) {
        this.attendanceTime = localTime;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public static Week findByAttendanceTime(final LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();

        return Arrays.stream(Week.values())
                .filter(week -> week.name().equals(day.name()))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(
                        String.format(localDateTime.format(NON_SCHOOL_DAY_FORMAT) + "은 등교일이 아닙니다.")));
    }
}
