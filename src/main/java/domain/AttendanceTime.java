package domain;

import java.time.LocalDateTime;

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
}
