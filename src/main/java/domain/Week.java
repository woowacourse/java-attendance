package domain;

import exception.WeekException;
import java.time.DayOfWeek;
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

    public static Week findByAttendanceTime(final AttendanceDateTime attendanceDateTime) {
        final DayOfWeek dayOfWeek = attendanceDateTime.getDayOfWeek();
        final String dayKoreanName = DayOfWeekKorean.getKoreanName(dayOfWeek);

        return Arrays.stream(Week.values())
                .filter(week -> week.name().equals(dayOfWeek.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        getMessageFormat(attendanceDateTime, dayKoreanName)));
    }

    private static String getMessageFormat(final AttendanceDateTime attendanceDateTime, final String dayKoreanName) {
        return String.format(WeekException.INVALID_ATTENDANCE_DAY.getMessage(
                Constants.FIXED_MONTH,
                attendanceDateTime.getDayOfMonth(),
                dayKoreanName));
    }
}
