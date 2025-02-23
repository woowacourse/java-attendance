package attendance.domain;

import static attendance.error.ErrorMessage.NOT_OPERATING_HOLIDAY;
import static attendance.error.ErrorMessage.NOT_OPERATING_TIME;
import static attendance.error.ErrorMessage.NOT_OPERATING_WEEKEND;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private final LocalDateTime attendanceTime;

    private AttendanceTime(LocalDateTime attendanceTime) {
        validateAttendanceTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceTime from(LocalDateTime attendanceTime) {
        return new AttendanceTime(attendanceTime);
    }

    public AttendanceType calculateAttendanceType() {
        DayOfWeek dayOfWeek = getDayOfWeek(attendanceTime.toLocalDate());
        int lateTime = dayOfWeek.calculateLateTime(attendanceTime.toLocalTime());
        return AttendanceType.decideType(lateTime);
    }

    public boolean isSameDate(LocalDate findDate) {
        LocalDate currentDate = attendanceTime.toLocalDate();
        return currentDate.isEqual(findDate);
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    private void validateAttendanceTime(LocalDateTime attendanceTime) {
        ifHolidayOrWeekendsThrowException(attendanceTime.toLocalDate());
        checkOperatingTime(attendanceTime.toLocalTime());
    }

    private void ifHolidayOrWeekendsThrowException(LocalDate currentDate) {
        ifHolidayThrowException(currentDate);
        ifWeekendThrowException(currentDate);
    }

    private void ifHolidayThrowException(LocalDate currentDate) {
        if (Holiday.isHoliday(currentDate)) {
            throw new IllegalArgumentException(NOT_OPERATING_HOLIDAY.getMessage());
        }
    }

    private void ifWeekendThrowException(LocalDate currentDate) {
        DayOfWeek dayOfWeek = getDayOfWeek(currentDate);
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(NOT_OPERATING_WEEKEND.getMessage());
        }
    }

    private void checkOperatingTime(LocalTime attendanceTime) {
        if (OperatingTime.isOperate(attendanceTime)) {
            return;
        }
        throw new IllegalArgumentException(NOT_OPERATING_TIME.getMessage());
    }

    private DayOfWeek getDayOfWeek(LocalDate currentDate) {
        return DayOfWeek.calculateDayOfWeek(currentDate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceTime);
    }
}
