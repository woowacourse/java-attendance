package domain.policy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import view.ErrorMessage;

public class DatePolicy {
    private static final String HOLIDAYS = "공휴일";
    private static final List<Integer> HOLIDAYS_DATES = List.of(25);

    // 날짜 정책 1. 출석 수정 시 값을 입력받는 현재 시점보다 미래일 수 없다.
    public static void validateIsDateFuture(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_FUTURE_CAN_NOT_BE_MODIFIED.getFormat());
        }
    }

    // 날짜 정책 2. 공휴일에는 출석을 받지 않는다.
    public static void validateIsDateHoliday(LocalDate date) {
        if (HOLIDAYS_DATES.contains(date.getDayOfMonth())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(
                    date.getMonthValue(), date.getDayOfMonth(), HOLIDAYS
            ));
        }
    }

    // 날짜 정책 3. 주말에는 출석을 받지 않는다.
    public static void validateIsDateWeekend(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(
                    date.getMonthValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
            ));
        }
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(
                    date.getMonthValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
            ));
        }
    }

    public static boolean isNotTrainingDay(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                HOLIDAYS_DATES.contains(date.getDayOfMonth());
    }
}