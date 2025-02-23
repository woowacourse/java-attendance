package attendance;

import attendance.model.AttendanceDetail;
import attendance.model.CustomClock;
import attendance.model.FixedCustomClock;
import attendance.model.WoowaDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class TestUtil {
    private static final CustomClock FIXED_CUSTOM_CLOCK = new FixedCustomClock(
            LocalDate.of(2024, 12, 16),
            Set.of(LocalDate.of(2024, 12, 25))
    );
    private static final LocalDate startDate = LocalDate.of(2024, 12, 1);

    public static WoowaDate createTestWoowaDate(LocalDate localDate) {
        return new WoowaDate(localDate, FIXED_CUSTOM_CLOCK); // CustomClock 없이 기본 생성
    }

    public static AttendanceDetail creatAttendanceDetail(int year, int month, int day, int hour, int minute) {
        WoowaDate woowaDate = createTestWoowaDate(LocalDate.of(year, month, day));
        return new AttendanceDetail(woowaDate, LocalTime.of(hour, minute));
    }

    public static CustomClock getClock() {
        return FIXED_CUSTOM_CLOCK;
    }

    public static CustomClock clockOf(int todayDate) {
        return new CustomClock() {
            @Override
            public LocalDateTime now() {
                return LocalDateTime.of(2024, 12, todayDate, 10, 0);
            }

            @Override
            public LocalDate nowDate() {
                return LocalDate.of(2024, 12, todayDate);
            }

            @Override
            public boolean isHoliday(LocalDate date) {
                return false;
            }
        };
    }

    public static LocalDate getTrainingStartDate() {
        return startDate;
    }
}
