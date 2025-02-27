package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDate {

    private static final LocalDate EDUCATION_START_DATE = LocalDate.of(2024, 12, 1);
    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));
    private static final LocalDate JANUARY_START_DATE = LocalDate.of(2025, 1, 1);

    private static final LocalTime MONDAY_EDUCATION_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime EDUCATION_START_TIME = LocalTime.of(10, 0);

    private final LocalDate attendanceDate;

    public AttendanceDate(final LocalDate date) {
        validateCampusOpen(date);
        this.attendanceDate = date;
    }

    public LocalTime getEducationStartTime() {
        if (attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return MONDAY_EDUCATION_START_TIME;
        }
        return EDUCATION_START_TIME;
    }

    public static List<LocalDate> getPastEducationDates(LocalDate nowDate) {
        if (nowDate.isAfter(JANUARY_START_DATE)) {
            nowDate = JANUARY_START_DATE;
        }
        List<LocalDate> educationDates = new ArrayList<>();
        for (LocalDate date = EDUCATION_START_DATE; date.isBefore(nowDate); date = date.plusDays(1)) {
            addEducationDate(date, educationDates);
        }
        return educationDates;
    }

    public boolean isSameAs(AttendanceDate date) {
        return this.attendanceDate.equals(date.attendanceDate);
    }

    public int getMonthValue() {
        return this.attendanceDate.getMonthValue();
    }

    public int getDayOfMonth() {
        return this.attendanceDate.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return this.attendanceDate.getDayOfWeek();
    }

    private void validateCampusOpen(LocalDate date) {
        if (isCampusClosed(date)) {
            throw new IllegalArgumentException("[ERROR] %d월 %d일 %s일은 등교일이 아닙니다.");
        }
    }

    private static boolean isCampusClosed(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || HOLIDAYS.contains(date);
    }

    private static void addEducationDate(LocalDate date, List<LocalDate> educationDates) {
        if (!isCampusClosed(date)) {
            educationDates.add(date);
        }
    }
}
