package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static attendance.domain.exception.HolidayExceptionMessage.NOT_OPEN_DATE_EXCEPTION;

public class Holiday {
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final int CHRISTMAS = 25;

    public static void isHoliday(final LocalDateTime attendanceDateTime) {
        if (checkHoliday(attendanceDateTime)) {
            throw new IllegalArgumentException(NOT_OPEN_DATE_EXCEPTION);
        }
    }

    public static boolean checkHoliday(final LocalDateTime attendanceDateTime) {
        return WEEKEND.contains(attendanceDateTime.getDayOfWeek()) || attendanceDateTime.getDayOfMonth() == CHRISTMAS;
    }
}
