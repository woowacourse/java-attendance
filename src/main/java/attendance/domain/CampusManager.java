package attendance.domain;

import attendance.exception.NotOperationDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class CampusManager {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final LocalTime OPERATION_TIME_BEGIN_THRESHOLD = LocalTime.of(7, 59);
    private static final LocalTime OPERATION_TIME_END_THRESHOLD = LocalTime.of(23, 01);

    private CampusManager() {
    }

    public static void validateOperationDate(final LocalDate date) {
        if (!isOperationDate(date)) {
            throw new NotOperationDateException(date);
        }
    }

    public static boolean isOperationDate(final LocalDate date) {
        return !isHoliday(date) &&
                !isWeekend(date);
    }

    private static boolean isHoliday(final LocalDate date) {
        return date.isEqual(CHRISTMAS);
    }

    private static boolean isWeekend(final LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.equals(DayOfWeek.SATURDAY) ||
                day.equals(DayOfWeek.SUNDAY);
    }

    public static void validateOperationTime(final LocalTime time) {
        boolean isOperationTime = isOperationTime(time);
        if (!isOperationTime) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다.");
        }
    }

    private static boolean isOperationTime(final LocalTime time) {
        return time.isAfter(OPERATION_TIME_BEGIN_THRESHOLD) &&
                time.isBefore(OPERATION_TIME_END_THRESHOLD);
    }
}
