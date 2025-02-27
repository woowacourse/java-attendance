import exception.NotOperatingTimeException;

import java.time.LocalTime;

public class AttendanceTime {
    private final LocalTime OPERATING_START = LocalTime.of(8, 0);
    private final LocalTime OPERATING_END = LocalTime.of(23, 0);

    private final LocalTime time;

    public AttendanceTime(LocalTime time) {
        validateTime(time);
        this.time = time;
    }

    public LocalTime getValue() {
        return time;
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(OPERATING_START) || time.isAfter(OPERATING_END)) {
            throw new NotOperatingTimeException();
        }
    }
}
