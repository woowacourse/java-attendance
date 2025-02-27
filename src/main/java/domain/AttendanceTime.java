package domain;

import domain.policy.AttendancePolicy;
import util.FormatUtil;

import java.time.LocalTime;

public class AttendanceTime {

    private final LocalTime time;

    private AttendanceTime(LocalTime time,
                           AttendancePolicy attendancePolicy) {
        validate(time, attendancePolicy);
        this.time = time;
    }

    public static AttendanceTime of(LocalTime time,
                                    AttendancePolicy attendancePolicy) {
        return new AttendanceTime(time, attendancePolicy);
    }

    private void validate(LocalTime time,
                          AttendancePolicy attendancePolicy) {
        if (attendancePolicy.canAttendTime(time)) {
            return;
        }
        throw new IllegalArgumentException(String.format("%s는 등교할 수 없는 시간입니다.",
                time.format(FormatUtil.TIME_FORMATTER)));
    }

    public LocalTime toLocalTime() {
        return time;
    }
}
