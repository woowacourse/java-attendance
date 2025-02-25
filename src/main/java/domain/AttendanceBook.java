package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceBook {
    private final Map<LocalDate, LocalTime> attendanceBook;
    private final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private final LocalTime START_TIME = LocalTime.of(10, 0);


    public AttendanceBook() {
        attendanceBook = new HashMap<>();
    }

    public LocalDateTime getAttendanceDateTimeByDate(LocalDate date) {
        return LocalDateTime.of(date, attendanceBook.get(date));
    }

    public void attendance(LocalDate date, LocalTime time) {
        if (isNotOperatingHours(time)) {
            throw new IllegalArgumentException();
        }
        if(isHoliday(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, time);
    }

    public boolean hasAttendanceRecord(LocalDate date) {
        return attendanceBook.containsKey(date);
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0));
    }

    public int getAbsenceCount() {
        int absenceCount = 0;
        for(LocalDate date = LocalDate.of(2024, 12, 1); !date.isAfter(AttendanceSystem.TODAY); date = date.plusDays(1)) {
            if(isHoliday(date)) {
                continue;
            }
            if(isAbsence(date)) {
                absenceCount ++;
            }
        }
        return absenceCount;
    }

    private boolean isAbsence(LocalDate date) {
        return !attendanceBook.containsKey(date) || attendanceBook.get(date).isAfter(startTime(date));
    }

    private LocalTime startTime(LocalDate date) {
        if(date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return MONDAY_START_TIME;
        }
        return START_TIME;
    }

    private boolean isHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return isWeekend(day) || isChristmas(date);
    }

    private boolean isChristmas(LocalDate date) {
        return Objects.equals(date, CHRISTMAS);
    }

    private static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }


}
