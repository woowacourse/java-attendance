package model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import model.policy.CampusOperatingPolicy;

public class AttendanceTime {
    public static final LocalTime DEFAULT_TIME = LocalTime.MAX;

    private final LocalTime time;

    public AttendanceTime(final LocalTime time) {
        validateCampusOperatingTime(time);
        this.time = time;
    }

    public Duration getOverDuration(final LocalTime compareTime) {
        return Duration.between(compareTime, time);
    }

    public LocalTime getTime() {
        return time;
    }

    private void validateCampusOperatingTime(final LocalTime time) {
        if (!CampusOperatingPolicy.isOperatingTime(time)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }
}
