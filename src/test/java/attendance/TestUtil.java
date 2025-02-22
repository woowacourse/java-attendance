package attendance;

import attendance.model.AttendanceDetail;
import attendance.model.FixedCustomClock;
import attendance.model.WoowaDate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class TestUtil {
    private static final FixedCustomClock FIXED_CUSTOM_CLOCK = new FixedCustomClock(
            Set.of(LocalDate.of(2024, 12, 25))
    );

    public static WoowaDate createTestWoowaDate(LocalDate localDate) {
        return new WoowaDate(localDate, FIXED_CUSTOM_CLOCK); // CustomClock 없이 기본 생성
    }

    public static AttendanceDetail creatAttendanceDetail(int year, int month, int day, int hour, int minute) {
        WoowaDate woowaDate = createTestWoowaDate(LocalDate.of(year, month, day));
        return new AttendanceDetail(woowaDate, LocalTime.of(hour, minute));
    }

}
