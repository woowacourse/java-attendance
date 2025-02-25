package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Day {

    private static final Integer STANDARD_LATE_MINUTE = 5;
    private static final Integer STANDARD_ABSENT_MINUTE = 30;

    private final LocalDate date;

    public Day(LocalDate date) {
        checkOffDay(date);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    private DayOfWeek getDayOfWeek(LocalDate date) {
        return DayOfWeek.getInstance(date);
    }

    public Boolean isEqualTo(LocalDate date) {
        return this.date.equals(date);
    }

    public void checkOffDay(LocalDate date) {
        if (checkWeekend(date) || checkHoliday(date)) {
            throw new IllegalArgumentException("[ERROR] 휴일 객체는 생성할 수 없습니다.");
        }
    }

    private boolean checkWeekend(LocalDate date) {
        return getDayOfWeek(date).equals(DayOfWeek.SATURDAY) || getDayOfWeek(date).equals(DayOfWeek.SUNDAY);
    }

    private boolean checkHoliday(LocalDate date) {
        return Holiday.isHoliday(date);
    }

    public boolean isLate(LocalTime attendanceTime) {
        LocalTime standardTime = getDayOfWeek(date).getStandardTime();
        if (standardTime == null) {
            return false;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_LATE_MINUTE && betweenMinutes <= STANDARD_ABSENT_MINUTE;
    }

    public boolean isAbsent(LocalTime attendanceTime) {
        LocalTime standardTime = getDayOfWeek(date).getStandardTime();
        if (standardTime == null) {
            return false;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_ABSENT_MINUTE;
    }

}
