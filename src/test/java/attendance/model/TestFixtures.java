package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestFixtures {

    protected static final Nickname BELLO_NICKNAME = new Nickname("벨로");
    protected static final Nickname NEO_NICKNAME = new Nickname("네오");
    protected static final Nickname BROWN_NICKNAME = new Nickname("브라운");

    protected static final LocalDate LOCAL_DATE_2024_12_02 = LocalDate.of(2024, 12, 2);
    protected static final LocalTime LOCAL_TIME_10_00 = LocalTime.of(10, 0);
    protected static final LocalDateTime LOCAL_DATE_TIME_2024_12_02_10_00 = LocalDateTime.of(
            LOCAL_DATE_2024_12_02, LOCAL_TIME_10_00);

    private TestFixtures() {
    }

    protected static AttendanceLog createAttendanceLog(Nickname nickname,
                                                       LocalDate attendanceDate,
                                                       LocalTime attendanceTime) {
        return new AttendanceLog(nickname, attendanceDate, attendanceTime);
    }
}
