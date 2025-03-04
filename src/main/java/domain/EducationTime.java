package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum EducationTime {
    MONDAY(DayOfWeek.MONDAY, 13, 0),
    TUESDAY(DayOfWeek.TUESDAY, 10, 0),
    WEDNESDAY(DayOfWeek.WEDNESDAY, 10, 0),
    THURSDAY(DayOfWeek.THURSDAY, 10, 0),
    FRIDAY(DayOfWeek.FRIDAY, 10, 0);

    private final DayOfWeek dayOfWeek;
    private final LocalTime educationStartTime;

    EducationTime(final DayOfWeek dayOfWeek, final int startHour, final int startMinute) {
        this.dayOfWeek = dayOfWeek;
        this.educationStartTime = LocalTime.of(startHour, startMinute);
    }

    public static LocalTime getEducationStartTime(final LocalDate targetDate) {
        OperationTime.checkIsOperationDate(targetDate);
        return Arrays.stream(EducationTime.values())
                .filter(educationTime -> educationTime.dayOfWeek == targetDate.getDayOfWeek())
                .map(educationTime -> educationTime.educationStartTime)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("교육 시작 시간 판정 실패"));
    }
}
