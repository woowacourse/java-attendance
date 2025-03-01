package attendance.fixture;

import attendance.domain.checker.CampusTime;
import java.time.LocalTime;

public class CampusTimeFixture {

    public static final LocalTime CAMPUS_START_TIME = CampusTime.START_TIME.getTime();
    public static final LocalTime CAMPUS_END_TIME = CampusTime.END_TIME.getTime();
}
