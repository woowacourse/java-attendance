package domain;

import java.util.Objects;
import java.util.Optional;

public class WorkTime implements Comparable<WorkTime> {
    private final Integer hour;
    private final Integer minute;

    public WorkTime(Integer hour, Integer minute) {
        validateSize(hour, minute);
        validateCampusTime(hour);
        this.hour = hour;
        this.minute = minute;
    }

    private void validateSize(Integer hour, Integer minute) {
        if (hour != null && (hour < 0 || hour > 23)) {
            throw new IllegalArgumentException("시간은 0 이상 23 이하여야 합니다.");
        }

        if (minute != null && (minute < 0 || minute > 59)) {
            throw new IllegalArgumentException("분은 0 이상 59 이하여야 합니다.");
        }
    }

    private void validateCampusTime(Integer hour) {
        if (hour != null && (hour < 8 || hour > 18)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isNull() {
        return hour == null && minute == null;
    }

    public Optional<Integer> getHour() {
        return Optional.ofNullable(hour);
    }

    public Optional<Integer> getMinute() {
        return Optional.ofNullable(minute);
    }

    @Override
    public int compareTo(WorkTime other) {
        if (this.getHour().isEmpty() && other.getHour().isEmpty()) {
            return 0;
        }
        if (this.getHour().isEmpty()) {
            return -1;
        }
        if (other.getHour().isEmpty()) {
            return 1;
        }

        int hourDiff = Integer.compare(this.getHour().orElse(0), other.getHour().orElse(0));
        if (hourDiff != 0) {
            return hourDiff;
        }

        return Integer.compare(this.getMinute().orElse(0), other.getMinute().orElse(0));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkTime workTime = (WorkTime) o;
        return Objects.equals(hour, workTime.hour) && Objects.equals(minute, workTime.minute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }
}
