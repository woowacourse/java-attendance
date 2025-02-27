package attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class Holiday {
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final int CHRISTMAS = 25;

    public static void isHoliday(final LocalDateTime attendanceDateTime) {
        if (checkHoliday(attendanceDateTime)) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    public static boolean checkHoliday(final LocalDateTime attendanceDateTime) {
        return WEEKEND.contains(attendanceDateTime.getDayOfWeek()) || attendanceDateTime.getDayOfMonth() == CHRISTMAS;
    }
}
