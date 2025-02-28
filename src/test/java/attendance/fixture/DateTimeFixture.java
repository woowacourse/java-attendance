package attendance.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeFixture {

    public static final LocalDate MONDAY = LocalDate.of(2025,2,24);
    public static final LocalDate OTHER_DAY = LocalDate.of(2025,2,25);
    public static final LocalDate SATURDAY = LocalDate.of(2025,3,1);
    public static final LocalDate SUNDAY = LocalDate.of(2025,3,2);

    public static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    public static final LocalTime OTHER_DAY_START_TIME = LocalTime.of(10, 0);
}
