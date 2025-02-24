package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.DayOfWeekConverter;

public class DateValidator {
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
    private static final int CHRISTMAS = 25;

    public void validateAttendanceChangeDate(int date, LocalDateTime today) {
        checkHoliday(date, today);
        checkFuture(date, today);
    }

    public void validateAttendanceCheckDate(LocalDateTime today) {
        checkHoliday(today.getDayOfMonth(), today);
    }

    private void checkFuture(int date, LocalDateTime today) {
        if (today.getDayOfMonth() < date) {
            throw new IllegalArgumentException("미래 날짜는 수정할 수 없습니다.");
        }
    }

    private void checkHoliday(int date, LocalDateTime today) {
        String message = String.format("%d월 %d일 %s은 등교일이 아닙니다.", today.getMonthValue(), date,
                DayOfWeekConverter.convertDayOfWeek(date, today));
        if (isHoliday(date, today)) {
            throw new IllegalArgumentException(message);
        }
    }

    public boolean isHoliday(int date, LocalDateTime today) {
        LocalDate targetDate = LocalDate.of(today.getYear(), today.getMonth(), date);
        if (date == CHRISTMAS) {
            return true;
        }
        if (targetDate.getDayOfWeek().getValue() == SATURDAY || targetDate.getDayOfWeek().getValue() == SUNDAY) {
            return true;
        }
        return false;
    }
}
