package attendance.domain;

import java.time.LocalDateTime;
import java.time.Month;

public class AttendanceDateTime implements SystemDateTime {
    private static final LocalDateTime NOW_DATETIME
        = LocalDateTime.of(2024, Month.DECEMBER, 26, 10, 4);

    @Override
    public LocalDateTime now() {
        return NOW_DATETIME;
    }
}
