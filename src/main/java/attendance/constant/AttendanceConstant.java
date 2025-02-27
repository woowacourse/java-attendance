package attendance.constant;

import java.time.LocalTime;

public class AttendanceConstant {

    private AttendanceConstant() {
    }

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 00);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 00);
}
