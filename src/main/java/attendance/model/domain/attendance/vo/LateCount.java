package attendance.model.domain.attendance.vo;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDateTime;
import java.util.List;

public class LateCount {

    private static final int LATE_TO_ABSENCE_COUNT = 3;

    private final int value;

    private LateCount(final int value) {
        validate(value);
        this.value = value;
    }

    public static LateCount fromDateTimes(final List<LocalDateTime> dateTimes) {
        final long lateCount = dateTimes.stream()
                .filter(AttendanceStatus::isLate)
                .count();

        return new LateCount(Math.toIntExact(lateCount));
    }

    private void validate(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("지각 횟수는 음수가 될 수 없습니다.");
        }
    }

    public int calculatePolicyAppliedAbsenceCount() {
        return value / LATE_TO_ABSENCE_COUNT;
    }

    public int calculatePolicyValue() {
        return value % LATE_TO_ABSENCE_COUNT;
    }

    public int getValue() {
        return value;
    }
}
