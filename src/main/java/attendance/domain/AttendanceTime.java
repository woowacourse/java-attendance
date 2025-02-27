package attendance.domain;

import java.util.Optional;

public class AttendanceTime {

    private final Integer hour;
    private final Integer minute;

    public AttendanceTime(
        final Integer hour,
        final Integer minute
    ) {
        validateTime(hour, minute);
        validateCampusTime(hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    private void validateTime(
        final Integer hour,
        final Integer minute
    ) {
        if (hour != null && (hour < 0 || hour > 23)) {
            throw new IllegalArgumentException("시간은 0 이상 23 이하여야 합니다.");
        }

        if (minute != null && (minute < 0 || minute > 59)) {
            throw new IllegalArgumentException("분은 0 이상 59 이하여야 합니다.");
        }
    }

    private void validateCampusTime(
        final Integer hour,
        final Integer minute
    ) {
        if (hour == null || minute == null) {
            return;
        }

        if ((hour < 8 || hour > 23 || (hour == 23 && minute > 0))) {
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
