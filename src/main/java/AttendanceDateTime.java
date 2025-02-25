import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class AttendanceDateTime {
    private LocalDateTime localDateTime;

    private AttendanceDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;

    }

    public static AttendanceDateTime of(int year, int month, int day, int hour, int minute) {
        return new AttendanceDateTime(LocalDateTime.of(year, month, day, hour, minute));
    }

    public boolean isRestDay() {
        DayOfWeek currentDayOfWeek = localDateTime.getDayOfWeek();
        if (currentDayOfWeek == DayOfWeek.SATURDAY || currentDayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        if (Holiday.holidays.contains(localDateTime.getDayOfMonth())) {
            return true;
        }
        return false;
    }

    public AttendanceType getAttendanceType() {
        DayOfWeek currentDayOfWeek = localDateTime.getDayOfWeek();
        int attendanceHourThreshold = 10;
        if (currentDayOfWeek == DayOfWeek.MONDAY) {
            attendanceHourThreshold = 13;
        }

        if (localDateTime.getHour() < attendanceHourThreshold || (localDateTime.getHour() == attendanceHourThreshold && localDateTime.getMinute() == 0)) {
            return AttendanceType.ATTENDANCE;
        }
        if (localDateTime.getHour() == attendanceHourThreshold && localDateTime.getMinute() <= 30) {
            return AttendanceType.LATE;
        }
        return AttendanceType.ABSENCE;
    }
}
