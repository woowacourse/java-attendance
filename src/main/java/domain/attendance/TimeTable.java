package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
            LocalTime.of(18,0));

    public static final int CAMPUS_OPERATING_OPENING_HOUR = 8;
    public static final int CAMPUS_OPERATING_CLOSING_HOUR = 23;
    public static final int OPERATING_MINUTE = 0;

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

    public static boolean isBeforeTardyTimeLimit(DayOfWeek dayOfWeek, LocalTime attendTime){
        return Arrays.stream(values())
                .filter(timeTable -> timeTable.getDays().contains(dayOfWeek))
                .anyMatch(timeTable -> {
                    LocalTime tardyTime = timeTable.getOpeningTime().plusMinutes(TARDY_LIMIT_MIN);
                    return attendTime.isBefore(tardyTime) || attendTime.equals(tardyTime);
                });
    }

    public static boolean isOverTardyTimeLimit(DayOfWeek dayOfWeek, LocalTime attendTime){
        return Arrays.stream(values())
                .filter(timeTable -> timeTable.getDays().contains(dayOfWeek))
                .anyMatch(timeTable -> {
                    LocalTime tardyTime = timeTable.getOpeningTime().plusMinutes(TARDY_LIMIT_MIN);
                    LocalTime absenceTime = timeTable.getOpeningTime().plusMinutes(ABSENCE_LIMIT_MIN);
                    return attendTime.isAfter(tardyTime) || attendTime.equals(absenceTime);
                });
    }

    public static boolean isOverAbsenceTimeLimit(DayOfWeek dayOfWeek, LocalTime attendTime){
        return Arrays.stream(values())
                .filter(timeTable -> timeTable.getDays().contains(dayOfWeek))
                .anyMatch(timeTable -> {
                    LocalTime absenceTime = timeTable.getOpeningTime().plusMinutes(ABSENCE_LIMIT_MIN);
                    return attendTime.isAfter(absenceTime);
                });
    }

    public static boolean isAttendanceDay(LocalDate date){
        return !isWeekend(date) && !Holiday.isHoliday(date);
    }

    private static boolean isWeekend(LocalDate date){
        return date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY;
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
