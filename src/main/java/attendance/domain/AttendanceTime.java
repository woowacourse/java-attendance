package attendance.domain;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceTime implements Comparable<AttendanceTime> {

    public static final AttendanceTime EMPTY = new AttendanceTime(null, null);

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

    public static AttendanceTime from(final LocalTime localTime) {
        return new AttendanceTime(localTime.getHour(), localTime.getMinute());
    }

    public Optional<Integer> getHour() {
        return Optional.ofNullable(hour);
    }

    public Optional<Integer> getMinute() {
        return Optional.ofNullable(minute);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttendanceTime that = (AttendanceTime) o;

        return Objects.equals(hour, that.hour)
            && Objects.equals(minute, that.minute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public int compareTo(final AttendanceTime o) {
        int hourComparison = compareHours(o);
        if (hourComparison != 0) {
            return hourComparison;
        }
        return compareMinutes(o);
    }

    private int compareHours(final AttendanceTime o) {
        if (this.hour == null && o.hour == null) {
            return 0;
        }
        if (this.hour == null) {
            return -1;
        }
        if (o.hour == null) {
            return 1;
        }
        return Integer.compare(this.hour, o.hour);
    }

    private int compareMinutes(final AttendanceTime o) {
        if (this.minute == null && o.minute == null) {
            return 0;
        }
        if (this.minute == null) {
            return -1;
        }
        if (o.minute == null) {
            return 1;
        }
        return Integer.compare(this.minute, o.minute);
    }
}
