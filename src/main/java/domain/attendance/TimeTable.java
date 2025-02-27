package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

import static java.time.DayOfWeek.*;

public enum TimeTable {
    MON_ATTENDANCE_TIME(
            EnumSet.of(DayOfWeek.MONDAY),
            LocalTime.of(13,0),
            LocalTime.of(18,0)),
    WEEKDAYS_EXCEPT_MON_ATTENDANCE_TIME(EnumSet.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY),
            LocalTime.of(10,0),
            LocalTime.of(18,0)),
    CAMPUS_OPERATING_TIME(EnumSet.allOf(DayOfWeek.class),
            LocalTime.of(8,0),
            LocalTime.of(23,0));

    public static final int MON_ATTENDANCE_START_HOUR = 13;
    public static final int WEEKDAYS_EXCEPT_MON_ATTENDANCE_START_HOUR = 10;
    public static final int WEEKDAYS_ATTENDANCE_END_HOUR = 18;
    public static final int CAMPUS_OPERATING_OPENING_HOUR = 8;
    public static final int CAMPUS_OPERATING_CLOSING_HOUR = 23;
    public static final int OPERATING_MINUTE = 0;

    public static final LocalTime MON_ATTENDANCE_START = LocalTime.of(
            MON_ATTENDANCE_START_HOUR,
            OPERATING_MINUTE
    );
    public static final LocalTime WEEKDAYS_EXCEPT_MON_ATTENDANCE_START = LocalTime.of(
            WEEKDAYS_EXCEPT_MON_ATTENDANCE_START_HOUR,
            OPERATING_MINUTE
    );
    public static final LocalTime WEEKDAYS_ATTENDANCE_END = LocalTime.of(
            WEEKDAYS_ATTENDANCE_END_HOUR,
            OPERATING_MINUTE
    );
    public static final LocalTime CAMPUS_OPERATING_START = LocalTime.of(
            CAMPUS_OPERATING_OPENING_HOUR,
            OPERATING_MINUTE
    );
    public static final LocalTime CAMPUS_OPERATING_END = LocalTime.of(
            CAMPUS_OPERATING_CLOSING_HOUR,
            OPERATING_MINUTE
    );

    public static final int TARDY_LIMIT_MIN = 5;
    public static final int ABSENCE_LIMIT_MIN = 30;


    private final Set<DayOfWeek> days;
    private final LocalTime openingTime;
    private final LocalTime closingTime;

    TimeTable(Set<DayOfWeek> days, LocalTime openingTime, LocalTime closingTime) {
        this.days = days;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public static boolean isOnCampusOperatingTime(LocalTime localTime){
        return localTime.equals(CAMPUS_OPERATING_START) || localTime.equals(CAMPUS_OPERATING_END)
                || (localTime.isAfter(CAMPUS_OPERATING_START) && localTime.isBefore(CAMPUS_OPERATING_END));
    }

    public boolean isOverTardyTimeLimit(DayOfWeek dayOfWeek, LocalTime attendTime){
        if(dayOfWeek == MONDAY){
            LocalTime tardyTime = MON_ATTENDANCE_START.plusMinutes(TARDY_LIMIT_MIN);
            return attendTime.isAfter(tardyTime);
        }
        LocalTime tardyTime = WEEKDAYS_EXCEPT_MON_ATTENDANCE_START.plusMinutes(TARDY_LIMIT_MIN);
        return attendTime.isAfter(tardyTime);
    }

    public boolean isOverAbsenceTimeLimit(DayOfWeek dayOfWeek, LocalTime attendTime){
        if(dayOfWeek == MONDAY){
            LocalTime absenceTime = MON_ATTENDANCE_START.plusMinutes(ABSENCE_LIMIT_MIN);
            return attendTime.isAfter(absenceTime);
        }
        LocalTime absenceTime = WEEKDAYS_EXCEPT_MON_ATTENDANCE_START.plusMinutes(ABSENCE_LIMIT_MIN);
        return attendTime.isAfter(absenceTime);
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }
}
