package attendance.domain;

import java.util.Optional;

public class AttendanceTime {

    private final Integer hour;
    private final Integer minute;

    public AttendanceTime(
        final Integer hour,
        final Integer minute
    ) {
        validateCampusTime(hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    private void validateCampusTime(
        final Integer hour,
        final Integer minute
    ) {
        if (hour < 8 || hour > 23 || (hour == 23 && minute > 0)) {
            throw new IllegalArgumentException("출석 시간은 캠퍼스 운영시간만 지원합니다.");
        }
    }

    public Optional<Integer> getHour() {
        return Optional.ofNullable(hour);
    }

    public Optional<Integer> getMinute() {
        return Optional.ofNullable(minute);
    }
}
