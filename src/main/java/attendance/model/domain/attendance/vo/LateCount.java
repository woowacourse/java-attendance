package attendance.model.domain.attendance.vo;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDateTime;
import java.util.List;

public class LateCount {

    private static final int LATE_TO_ABSENCE_COUNT = 3;

    private final int value;

    private LateCount(int value) {
        validate(value);
        this.value = value;
    }

    public static LateCount fromDateTimes(List<LocalDateTime> dateTimes) {
        long value = dateTimes.stream()
                .filter(AttendanceStatus::isLate)
                .count();

        return new LateCount(Math.toIntExact(value));
    }

    public int calculatePolicyAppliedAbsenceCount() {
        return value / LATE_TO_ABSENCE_COUNT;
    }

    public int calculatePolicyAppliedLateCount() {
        return value % LATE_TO_ABSENCE_COUNT;
    }

    public int getValue() {
        return value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("지각 횟수는 음수가 될 수 없습니다.");
        }
    }
}
