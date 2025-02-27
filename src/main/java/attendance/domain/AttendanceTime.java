package attendance.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceTime {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    private final LocalTime attendanceTime;

    public AttendanceTime(final LocalTime attendanceTime) {
        validateOutOfCampusOperationTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateOutOfCampusOperationTime(final LocalTime attendanceTime) {
        if (isOutOfCampusOperationTime(attendanceTime)) {
            throw new IllegalArgumentException(
                    String.join("", TIME_FORMATTER.format(attendanceTime), "은 출석 시간이 아닙니다."));
        }
    }

    private boolean isOutOfCampusOperationTime(final LocalTime attendanceTime) {
        return CAMPUS_OPEN_TIME.isAfter(attendanceTime) || CAMPUS_CLOSE_TIME.isBefore(attendanceTime);
    }

    public boolean isBetweenInclusive(final LocalTime startInclusive, final LocalTime endInclusive) {
        return (attendanceTime.isAfter(startInclusive) || attendanceTime.equals(startInclusive))
                && (attendanceTime.isBefore(endInclusive) || attendanceTime.equals(endInclusive));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceTime that)) {
            return false;
        }
        return Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceTime);
    }

}
