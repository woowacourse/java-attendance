package domain;

import java.time.LocalTime;
import java.util.Objects;

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
        return Integer.compare(time.getMinute(), minute);
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) object;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }
}
