package domain;

import java.time.LocalTime;

public record AttendanceTime(
        LocalTime time,
        AttendanceStatus status
) {

}
