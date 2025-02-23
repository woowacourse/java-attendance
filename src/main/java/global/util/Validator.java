package global.util;

import view.ViewUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class Validator {
    public static void validateIsNotWorkingDay(LocalDate targetDate) {
        if (DateUtil.isNotWorkingDay(targetDate)) {
            String errorMessage = String.format("%d월 %02d일 %s은 등교일이 아닙니다.", targetDate.getMonthValue(), targetDate.getDayOfMonth(),
                    ViewUtil.getDayOfWeekToMessage(targetDate.getDayOfWeek()));
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateIsInOperationTime(LocalTime targetTime) {
        if (targetTime.isBefore(LocalTime.of(8, 0)) || targetTime.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public static void validateIsFutureDate(LocalDate targetDate) {
        if (targetDate.isAfter(DateUtil.FIXED_REFERENCE_DATE.toLocalDate())) {
            throw new IllegalArgumentException("미래 날짜는 출석할 수 없습니다.");
        }
    }
}
