package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum Campus {

    OPERATION(8, 23),
    EDUCATION_MONDAY(13, 18),
    EDUCATION_EXCEPT_MONDAY(10, 18);

    private final LocalTime startTime;
    private final LocalTime endTime;

    Campus(final int startHour, final int endHour) {
        this.startTime = LocalTime.of(startHour, 0);
        this.endTime = LocalTime.of(endHour, 0);
    }

    public static boolean isOperationTime(final LocalTime inputTime) {
        return (OPERATION.startTime.equals(inputTime) || OPERATION.startTime.isBefore(inputTime))
                && (OPERATION.endTime.equals(inputTime) || OPERATION.endTime.isAfter(inputTime));
    }

    public static LocalTime getEducationStartTime(final DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return EDUCATION_MONDAY.startTime;
        }
        return EDUCATION_EXCEPT_MONDAY.startTime;
    }
}
