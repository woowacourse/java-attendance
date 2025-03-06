package domain;

import config.AttendancePolicyConfig;
import util.FormatUtil;

import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private final LocalTime time;

    private AttendanceTime(LocalTime time) {
        validate(time);
        this.time = time;
    }

    public static AttendanceTime from(LocalTime time) {
        return new AttendanceTime(time);
    }

    private void validate(LocalTime time) {
        if (AttendancePolicyConfig.getInstance().canAttendTime(time)) {
            return;
        }
        throw new IllegalArgumentException(String.format("%s는 등교할 수 없는 시간입니다.",
                time.format(FormatUtil.TIME_FORMATTER)));
    }

    public LocalTime toLocalTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }
}
