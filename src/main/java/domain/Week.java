package domain;

import error.CustomIllegalArgumentException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import util.Constants;
import util.DayOfWeekKorean;

public enum Week {

    // 월요일만 다른 출석 시간
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0));

    private final LocalTime attendanceTime;

    Week(final LocalTime localTime) {
        this.attendanceTime = localTime;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public static Week findByAttendanceTime(final LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();
        final String dayOfWeek = DayOfWeekKorean.getKoreanName(localDateTime.getDayOfWeek());

        return Arrays.stream(Week.values())
                .filter(week -> week.name().equals(day.name()))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(
                        String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                                Constants.FIXED_MONTH,
                                localDateTime.getDayOfMonth(),
                                dayOfWeek)));
    }
}
