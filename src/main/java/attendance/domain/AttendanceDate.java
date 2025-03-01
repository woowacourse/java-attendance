package attendance.domain;

import static attendance.constant.ErrorMessage.INVALID_ATTEND_DATE;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceDate(
        LocalDate date
) {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public static AttendanceDate from(final LocalDate date) {
        validateAvailableAttendDate(date);
        return new AttendanceDate(date);
    }

    public boolean isEqualToDate(final LocalDate date) {
        return this.date.isEqual(date);
    }

    private static void validateAvailableAttendDate(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ||
                date.isEqual(CHRISTMAS)) {
            throw new IllegalArgumentException(String.format(INVALID_ATTEND_DATE.getMessage(),
                    date.getMonth().getValue(),
                    date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public boolean isMonday() {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }
}
