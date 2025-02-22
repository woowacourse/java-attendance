package attendance.domain;

import java.time.LocalTime;

public enum DayOfWeek {

    MONDAY("월요일", LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY("화요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY("수요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY("목요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY("금요일", LocalTime.of(10, 0), LocalTime.of(18, 0));

    private static final int ABSENCE_TIME = 31;

    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;

    DayOfWeek(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int calculateLateTime(LocalTime attendanceTime) {
        if (attendanceTime.getHour() - startTime.getHour() > 0) {
            return ABSENCE_TIME;
        }
        return attendanceTime.getMinute() - startTime.getMinute();
    }

    public String getName() {
        return name;
    }
}
