package attendance.domain;

import java.util.List;

public class InfoCheckAttendance {
    private static final int MONTH_INDEX = 0;
    private static final int DAY_INDEX = 1;
    private static final int DAY_OF_WEEK_INDEX = 2;
    private static final int ATTENDANCE_TIME_INDEX = 3;
    private static final int ATTENDANCE_TYPE_INDEX = 4;

    private final String month;
    private final String day;
    private final String dayOfWeek;
    private final String attendanceTime;
    private final String attendanceType;

    public InfoCheckAttendance(List<String> attendanceInfo) {
        this.month = attendanceInfo.get(MONTH_INDEX);
        this.day = attendanceInfo.get(DAY_INDEX);
        this.dayOfWeek = attendanceInfo.get(DAY_OF_WEEK_INDEX);
        this.attendanceTime = attendanceInfo.get(ATTENDANCE_TIME_INDEX);
        this.attendanceType = attendanceInfo.get(ATTENDANCE_TYPE_INDEX);
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceType() {
        return attendanceType;
    }
}
