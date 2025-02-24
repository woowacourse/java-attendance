package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDateHelper {
    public static final String NOT_SCHOOL_RUNNING_DAY = "휴일에는 출석할 수 없습니다.";
    private static final LocalDate SCHOOL_OPEN_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SCHOOL_OPEN_END_DATE = LocalDate.of(2024, 12, 31);

    public static boolean isWeekend(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }
}
