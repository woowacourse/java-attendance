package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum AttendanceDayOfWeek {
    MONDAY("월", 13, 18),
    TUESDAY("화", 10, 18),
    WEDNESDAY("수", 10, 18),
    THURSDAY("목", 10, 18),
    FRIDAY("금", 10, 18);

    private final String title;
    private final int startHour;
    private final int endHour;

    AttendanceDayOfWeek(
        final String title,
        final int startHour,
        final int endHour
    ) {
        this.title = title;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static AttendanceDayOfWeek from(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        validateWeekend(dayOfWeek);
        return AttendanceDayOfWeek.valueOf(dayOfWeek.name());
    }

    private static void validateWeekend(final DayOfWeek dayOfWeek) {
        if (dayOfWeek.getValue() > 5) {
            throw new IllegalArgumentException("주말은 출석 날짜로 지원하지 않습니다.");
        }
    }

    public String getTitle() {
        return title;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
