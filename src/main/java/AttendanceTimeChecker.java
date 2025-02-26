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
        if (requestedDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || requestedDate.getDayOfWeek()
                .equals(DayOfWeek.SUNDAY)) {
            throw new IllegalArgumentException("평일이거나 공휴일이 아닌 경우에만 출석할 수 있습니다.");
        }

        if (requestedDate.getMonth() == Month.DECEMBER && requestedDate.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("평일이거나 공휴일이 아닌 경우에만 출석할 수 있습니다.");
        }
    }
}
