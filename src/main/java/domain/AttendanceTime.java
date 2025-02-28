package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private final LocalDateTime time;

    public AttendanceTime(LocalDateTime time) {
        validateHoliday(time);
        validateOperationTime(time);
        this.time = time;
    }

    public boolean isSameDay(AttendanceTime otherTime) {
        return time.toLocalDate().equals(otherTime.time.toLocalDate());
    }

    public boolean isIn(LocalDate day) {
        return time.toLocalDate().equals(day);
    }

    public AttendanceTime changeTime(LocalTime newTime) {
        return new AttendanceTime(LocalDateTime.of(time.toLocalDate(), newTime));
    }

    private void validateOperationTime(LocalDateTime time) {
        if (!OperationSchedule.isInOperationTime(time)) {
            throw new IllegalArgumentException("운영시간이 아니면 출석할 수 없습니다.");
        }
    }

    private void validateHoliday(LocalDateTime time) {
        if (Holiday.isHoliday(time.toLocalDate())) {
            throw new IllegalArgumentException("주말과 공휴일에는 출석할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }
}
