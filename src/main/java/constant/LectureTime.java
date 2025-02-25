package constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;

public enum LectureTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    LectureTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static boolean isLectureDate(LocalDate date) {
        return Arrays.stream(values())
                .map(lectureTime -> lectureTime.dayOfWeek)
                .anyMatch(dayOfWeek1 -> dayOfWeek1 == date.getDayOfWeek());
    }

    // TODO: 날짜에 맞는 교육 시간을 반환한다, 교육 날이 아니면 예외를 발생시킨다
    public static LectureTime from(LocalDate date) {
        return Arrays.stream(values())
                .filter(lectureTime -> lectureTime.dayOfWeek == date.getDayOfWeek())
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(date.getDayOfWeek()
                                .getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                                + ": 교육이 없는 요일입니다.")
                );
    }

    public static int calculateElapsedMinutes(LocalDate date, LocalTime time) {
        if (!isLectureDate(date)) {
            throw new IllegalArgumentException(date + ": 교육이 없는 날입니다.");
        }
        return Math.max(0, (int) ChronoUnit.MINUTES.between(from(date).startTime, time));
    }
}
