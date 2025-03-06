package domain;

import java.util.Objects;

public record AttendanceTime(
        int hour,
        int minute
) {

    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;
    private static final int OPERATING_OPEN_HOUR = 8;
    private static final int OPERATING_CLOSE_HOUR = 23;

    public AttendanceTime {
        validateMinuteRange(minute);
        validateOperatingHour(hour);
    }

    public boolean isEqualHour(int hour) {
        return this.hour == hour;
    }

    public boolean isAfterMinute(int minute) {
        return this.minute > minute;
    }

    public boolean isEqualAndBeforeMinute(int minute) {
        return this.minute <= minute;
    }

    public boolean isBeforeHour(int hour) {
        return this.hour < hour;
    }

    public boolean isAfterHour(int hour) {
        return this.hour > hour;
    }

    private void validateMinuteRange(int minute) {
        if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
            throw new IllegalArgumentException("[ERROR] 분의 범위를 벗어났습니다");
        }
    }

    private void validateOperatingHour(int hour) {
        if (hour < OPERATING_OPEN_HOUR || hour > OPERATING_CLOSE_HOUR) {
            throw new IllegalArgumentException("[ERROR] 현재 운영시간이 아닙니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceTime that)) {
            return false;
        }
        return hour == that.hour && minute == that.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }
}
