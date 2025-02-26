package domain;

import java.time.LocalDateTime;

public class AttendanceTime {

    private final LocalDateTime time;

    public AttendanceTime(LocalDateTime time) {
        validateOperationTime(time);
        this.time = time;
    }

    private void validateOperationTime(LocalDateTime time) {
        if (!OperationSchedule.isInOperationTime(time)) {
            throw new IllegalArgumentException("운영시간이 아니면 출석할 수 없습니다.");
        }
    }
}
