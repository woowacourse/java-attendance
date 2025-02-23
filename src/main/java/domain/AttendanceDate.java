package domain;

import domain.rule.AttendanceDateRule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record AttendanceDate(LocalDate date) {

    public AttendanceDate {
        validate(date);
    }

    public static AttendanceDate from(LocalDate date) {
        return new AttendanceDate(date);
    }

    public boolean isSpecialDay() {
        return AttendanceDateRule.isSpecialDay(date);
    }

    private static void validate(LocalDate date) {
        validateWeekend(date.getDayOfWeek());
        validateHoliday(date);
    }

    public static void validateWeekend(DayOfWeek dayOfWeek) {
        if (AttendanceDateRule.isWeekend(dayOfWeek)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }

    private static void validateHoliday(LocalDate date) {
        if (AttendanceDateRule.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }
}
