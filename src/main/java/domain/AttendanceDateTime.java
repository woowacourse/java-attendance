package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class AttendanceDateTime {
    private final LocalDateTime dateTime;

    private AttendanceDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime of(int year, int month, int day, int hour, int minute) {
        return new AttendanceDateTime(LocalDateTime.of(year, month, day, hour, minute));
    }

    public static AttendanceDateTime from(LocalDateTime localDateTime) {
        return new AttendanceDateTime(localDateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isRestDay() {
        DayOfWeek currentDayOfWeek = dateTime.getDayOfWeek();
        if (currentDayOfWeek == DayOfWeek.SATURDAY || currentDayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        if (Holiday.holidays.contains(dateTime.getDayOfMonth())) {
            return true;
        }
        return false;
    }

    public boolean isSchoolTime() {
        int currentHour = dateTime.getHour();
        int currentMinute = dateTime.getMinute();
        if (currentHour >= 8 && currentHour <= 22) {
            return true;
        }
        if (currentHour == 23 && currentMinute == 0) {
            return true;
        }
        return false;
    }

    public AttendanceType getAttendanceType() {
        DayOfWeek currentDayOfWeek = dateTime.getDayOfWeek();
        int attendanceHourThreshold = 10;
        if (currentDayOfWeek == DayOfWeek.MONDAY) {
            attendanceHourThreshold = 13;
        }

        if (dateTime.getHour() < attendanceHourThreshold || (dateTime.getHour() == attendanceHourThreshold
                && dateTime.getMinute() == 0)) {
            return AttendanceType.ATTENDANCE;
        }
        if (dateTime.getHour() == attendanceHourThreshold && dateTime.getMinute() <= 30) {
            return AttendanceType.LATE;
        }
        return AttendanceType.ABSENCE;
    }
}
