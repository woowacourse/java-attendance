package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDateHelper {
    private static final LocalDate SCHOOL_OPEN_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SCHOOL_OPEN_END_DATE = LocalDate.of(2024, 12, 31);
    private static final LocalTime MONDAY_SCHOOL_OPEN_TIME = LocalTime.of(13, 0);
    private static final LocalTime MONDAY_SCHOOL_CLOSE_TIME = LocalTime.of(18, 0);
    private static final LocalTime NORMAL_SCHOOL_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime NORMAL_SCHOOL_CLOSE_TIME = LocalTime.of(18, 0);


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

    public static boolean isMonday(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public static LocalTime schoolOpenTime(LocalDate attendanceDate) {
        if (isMonday(attendanceDate)) {
            return MONDAY_SCHOOL_OPEN_TIME;
        }
        return NORMAL_SCHOOL_OPEN_TIME;
    }
}
