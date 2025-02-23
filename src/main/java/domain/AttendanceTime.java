package domain;

import java.time.LocalTime;

public class AttendanceTime {

    public static final int OPERATING_TIME_START = 8;
    public static final int OPERATING_TIME_END = 23;

    private final LocalTime time;

    public AttendanceTime(LocalTime time) {
        validateOperatingTime(time);
        this.time = time;
    }

    private void validateOperatingTime(LocalTime time) {
        if (time.getHour() < OPERATING_TIME_START || time.getHour() == OPERATING_TIME_END) {
            throw new IllegalArgumentException("[ERROR] 출석 시간이 아닙니다.");
        }
    }

    public int compareHour(int hour) {
        return Integer.compare(time.getHour(), hour);

    }

    public int compareMinute(int minute) {
        return Integer.compare(time.getHour(), minute);
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
