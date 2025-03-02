package domain;

import static util.Constants.ERROR_HEADER;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendancePolicy {
    private static final String HOLIDAY_ERROR = "주말 및 공휴일은 출석할 수 없습니다.";
    private static final String NOT_RUNNING_TIME_ERROR = "캠퍼스 운영시간이 아닙니다.";
    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);
    private static final List<Integer> HOLIDAY = List.of(25);

    public static void validateRunningTime(LocalTime time) {
        if(time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException(ERROR_HEADER + NOT_RUNNING_TIME_ERROR);
        };
    }

    public static void validateHoliday(LocalDate date) {
        if(isHoliday(date)) {
            throw new IllegalArgumentException(ERROR_HEADER + HOLIDAY_ERROR);
        }
    }

    public static boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || HOLIDAY.contains(date.getDayOfMonth());
    }
}
