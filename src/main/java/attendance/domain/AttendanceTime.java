package attendance.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AttendanceTime(LocalTime attendanceTime) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public AttendanceTime {
        validateOutOfCampusOperationTime(attendanceTime);
    }

    private void validateOutOfCampusOperationTime(final LocalTime attendanceTime) {
        if (CampusTime.isOutOfCampusOperationTime(attendanceTime)) {
            throw new IllegalArgumentException(
                    String.join("", TIME_FORMATTER.format(attendanceTime), "은 출석 시간이 아닙니다.")
            );
        }
    }

    public boolean isBetweenInclusive(final LocalTime startInclusive, final LocalTime endInclusive) {
        return (attendanceTime.isAfter(startInclusive) || attendanceTime.equals(startInclusive))
                && (attendanceTime.isBefore(endInclusive) || attendanceTime.equals(endInclusive));
    }

}
