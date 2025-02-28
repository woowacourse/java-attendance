import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class AttendanceTimeChecker {

    public static void checkTime(LocalTime requestedTime) {
        LocalTime openTime = LocalTime.of(8, 0);
        LocalTime closedTime = LocalTime.of(23, 0);
        if (requestedTime.isBefore(openTime) || requestedTime.isAfter(closedTime)) {
            throw new IllegalArgumentException("운영 시간 내에만 출석할 수 있습니다.");
        }
    }

    public static void checkDate(LocalDate requestedDate) {
        if (isWeekend(requestedDate) || isHoliday(requestedDate)) {
            throw new IllegalArgumentException("평일이거나 공휴일이 아닌 경우에만 출석할 수 있습니다.");
        }
    }

    public static boolean isAttendanceRequiredDate(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek()
                .equals(DayOfWeek.SUNDAY);
    }

    private static boolean isHoliday(LocalDate date) {
        return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() == 25;
    }
}
