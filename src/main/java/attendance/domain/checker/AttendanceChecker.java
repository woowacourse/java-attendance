package attendance.domain.checker;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceChecker {

    private final static LocalTime MONDAY_EDUCATION_START_TIME = LocalTime.of(13, 0, 0);
    private final static LocalTime NOT_MONDAY_EDUCATION_START_TIME = LocalTime.of(10, 0, 0);
    private final HolidayChecker holidayChecker;

    public AttendanceChecker(HolidayChecker holidayChecker) {
        this.holidayChecker = holidayChecker;
    }

    public AttendanceType checkAttendance(LocalDateTime arrivalDatetime) {
        holidayChecker.validateNotHoliday(arrivalDatetime.toLocalDate());
        CampusTime.validateCampusTime(arrivalDatetime.toLocalTime());
        if (arrivalDatetime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceType.parse(MONDAY_EDUCATION_START_TIME, arrivalDatetime.toLocalTime());
        }
        return AttendanceType.parse(NOT_MONDAY_EDUCATION_START_TIME, arrivalDatetime.toLocalTime());
    }
}
