package attendance.domain;

import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private static final LocalTime OPERATION_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATION_END_TIME = LocalTime.of(23, 0);

    private final LocalTime attendanceTime;

    public AttendanceTime(final LocalTime attendanceTime) {
        validateOperationTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateOperationTime(final LocalTime attendanceTime) {
        if (attendanceTime.isBefore(OPERATION_START_TIME) || attendanceTime.isAfter(OPERATION_END_TIME)) {
            throw new IllegalArgumentException("%02d:%02d은 캠퍼스 운영 시간이 아닙니다.".formatted(
                    attendanceTime.getHour(), attendanceTime.getMinute()
            ));
        }
    }

    public int calculateMinuteDifferences(final LocalTime startTime) {
        return (attendanceTime.getHour() - startTime.getHour()) * 60
                + (attendanceTime.getMinute() - startTime.getMinute());
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceTime that)) {
            return false;
        }
        return Objects.equals(getAttendanceTime(), that.getAttendanceTime());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAttendanceTime());
    }

}
