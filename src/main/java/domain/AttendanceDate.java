package domain;

import except.AttendanceException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record AttendanceDate(LocalDate date) {

    private static final String OUT_OF_SCHOOL_OPEN_DATE = "2024년 12월에만 출석할 수 있습니다.";
    private static final String NOT_SCHOOL_RUNNING_DAY = "휴일에는 출석할 수 없습니다.";
    private static final int MONDAY = 1;
    private static final LocalDate SCHOOL_OPEN_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SCHOOL_OPEN_END_DATE = LocalDate.of(2024, 12, 31);

    public AttendanceDate {
        validateAttendanceDate(date);
    }

    public static boolean isOutOfSchoolOpenDate(LocalDate attendanceDate) {
        return SCHOOL_OPEN_END_DATE.isBefore(attendanceDate) || SCHOOL_OPEN_START_DATE.isAfter(attendanceDate);
    }

    public static LocalDate schoolLastDate() {
        return SCHOOL_OPEN_END_DATE;
    }

    public static LocalDate schoolStartDate() {
        return SCHOOL_OPEN_START_DATE;
    }

    public static boolean isValidAttendanceDate(LocalDate date) {
        if (isWeekend(date) || isOutOfSchoolOpenDate(date)) {
            return false;
        }
        return true;
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (isWeekend(attendanceDate)) {
            throw new AttendanceException(NOT_SCHOOL_RUNNING_DAY);
        }
        if (isOutOfSchoolOpenDate(attendanceDate)) {
            throw new AttendanceException(OUT_OF_SCHOOL_OPEN_DATE);
        }
    }

    public boolean isMonday() {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue() == MONDAY;
    }

    public static boolean isWeekend(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    public boolean isBefore(LocalDate otherDate) {
        return date.isBefore(otherDate);
    }
}
