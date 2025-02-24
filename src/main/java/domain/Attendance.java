package domain;

import exception.AttendanceException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.Constants;
import util.DayOfWeekKorean;
import util.HolidayManager;

public class Attendance {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(final AttendanceDateTime attendanceDateTime) {
        validateHoliday(attendanceDateTime);
        final Week day = Week.findByAttendanceTime(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, attendanceDateTime.getTime());
    }

    public static void validate(final AttendanceDateTime attendanceDateTime) {
        validateHoliday(attendanceDateTime);
        Week.findByAttendanceTime(attendanceDateTime);
    }

    private static void validateHoliday(final AttendanceDateTime attendanceDateTime) {
        final int dayOfMonth = attendanceDateTime.getDayOfMonth();
        if (HolidayManager.isHoliday(dayOfMonth)) {
            throw new IllegalArgumentException(
                    String.format(AttendanceException.INVALID_ATTENDANCE_DAY.getMessage(
                            Constants.FIXED_MONTH,
                            dayOfMonth,
                            DayOfWeekKorean.getKoreanName(DayOfWeek.of(dayOfMonth)))));

        }
    }

    public boolean equals(final int findDayOfMonth) {
        return attendanceDateTime.getDayOfMonth() == findDayOfMonth;
    }

    public int getDateOfMonth() {
        return attendanceDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return attendanceDateTime.getDateTime();
    }

    public LocalTime getTime() {
        return attendanceDateTime.getTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public boolean equalsDate(final LocalDate date) {
        return attendanceDateTime.getDate().equals(date);
    }
}
