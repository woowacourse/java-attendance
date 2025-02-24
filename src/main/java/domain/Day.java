package domain;

import domain.constant.StandardDate;

import java.time.*;

public class Day {

    private static final Integer STANDARD_LATE_MINUTE = 5;
    private static final Integer STANDARD_ABSENT_MINUTE = 30;

    private final LocalDate date;

    public Day(LocalDate date) {
        this.date = date;
    }

    public Boolean isEqualTo(LocalDate date) {
        return this.date.equals(date);
    }

    public Boolean checkHoliday() {
        return Holiday.isHoliday(date)
                || date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public void validateNonHoliday() {
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());

        if (checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] " + date.getMonthValue() + "월 " + date.getDayOfMonth() + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }

    public void validateDayOfMonth(Integer dayOfMonth) {
        if (!StandardDate.TODAY.containsDayOfMonth(dayOfMonth)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 날짜입니다.");
        }
    }

    public boolean isLate(LocalTime attendanceTime) {
        LocalTime standardTime = getStandardTime();
        if (standardTime == null || attendanceTime == null) {
            return false;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_LATE_MINUTE && betweenMinutes <= STANDARD_ABSENT_MINUTE;
    }

    public boolean isAbsent(LocalTime attendanceTime) {
        LocalTime standardTime = getStandardTime();
        if (standardTime == null) {
            return false;
        }

        if (attendanceTime == null) {
            return true;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_ABSENT_MINUTE;
    }

    public boolean containsDayOfMonth(Integer dayOfMonth) {
        return YearMonth.from(date).isValidDay(dayOfMonth);
    }

    public Day createDay(Integer dayOfMonth) {
        return new Day(LocalDate.of(date.getYear(), date.getMonthValue(), dayOfMonth));
    }

    private LocalTime getStandardTime() {
        return AttendanceStandard.getInstance(date).getStandardTime();
    }

    public LocalDate getDate() {
        return date;
    }
}
