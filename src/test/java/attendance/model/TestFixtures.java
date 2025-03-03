package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestFixtures {

    protected static final Nickname BELLO_NICKNAME = new Nickname("벨로");
    protected static final Nickname NEO_NICKNAME = new Nickname("네오");
    protected static final Nickname BROWN_NICKNAME = new Nickname("브라운");

    protected static final LocalDate LOCAL_DATE_2024_12_02 = LocalDate.of(2024, 12, 2);
    protected static final LocalTime LOCAL_TIME_10_00 = LocalTime.of(10, 0);


    private TestFixtures() {
    }
}
