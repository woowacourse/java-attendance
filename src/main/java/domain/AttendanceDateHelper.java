package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDateHelper {
    private static final LocalDate SCHOOL_OPEN_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SCHOOL_OPEN_END_DATE = LocalDate.of(2024, 12, 31);

    public static boolean isWeekend(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    public static boolean isOutOfSchoolOpenDate(LocalDate attendanceDate) {
        return !(SCHOOL_OPEN_START_DATE.isBefore(attendanceDate) && SCHOOL_OPEN_END_DATE.isAfter(attendanceDate));
    }
}
