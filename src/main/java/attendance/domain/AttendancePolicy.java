package attendance.domain;

import static attendance.error.ErrorMessage.NOT_OPERATING_HOLIDAY;
import static attendance.error.ErrorMessage.NOT_OPERATING_TIME;
import static attendance.error.ErrorMessage.NOT_OPERATING_WEEKEND;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {

    public static AttendanceType checkAttendanceType(LocalDate currentDate, LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = getDayOfWeek(currentDate);
        checkOperatingTime(attendanceTime);
        int lateTime = dayOfWeek.calculateLateTime(attendanceTime);
        return AttendanceType.decideType(lateTime);
    }

    public static void ifHolidayOrWeekendsThrowException(LocalDate currentDate) {
        ifHolidayThrowException(currentDate);
        ifWeekendThrowException(currentDate);
    }

    private static void ifHolidayThrowException(LocalDate currentDate) {
        if (Holiday.isHoliday(currentDate)) {
            throw new IllegalArgumentException(NOT_OPERATING_HOLIDAY.getMessage());
        }
    }

    private static void ifWeekendThrowException(LocalDate currentDate) {
        DayOfWeek dayOfWeek = getDayOfWeek(currentDate);
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(NOT_OPERATING_WEEKEND.getMessage());
        }
    }

    private static void checkOperatingTime(LocalTime attendanceTime) {
        if (OperatingTime.isOperate(attendanceTime)) {
            return;
        }
        throw new IllegalArgumentException(NOT_OPERATING_TIME.getMessage());
    }

    private static DayOfWeek getDayOfWeek(LocalDate currentDate) {
        return DayOfWeek.calculateDayOfWeek(currentDate);
    }
}
