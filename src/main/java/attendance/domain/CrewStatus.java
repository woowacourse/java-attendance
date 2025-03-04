package attendance.domain;

import static attendance.domain.AttendanceType.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum CrewStatus {
    WARING("경고", value -> value == 2),
    INTERVIEW("면담", value -> value >= 3 && value < 5),
    FIRE("제적", value -> value >= 5),
    NORMAL("통과", value -> value < 2);

    private final String statusName;
    private final Predicate<Long> condition;

    CrewStatus(String statusName, Predicate<Long> condition) {
        this.statusName = statusName;
        this.condition = condition;
    }

    public static CrewStatus calculate(AttendanceResult attendanceResult) {
        Map<AttendanceType, Long> calculateResult = attendanceResult.getAttendanceResult();
        long statusDecisionValue = (calculateResult.getOrDefault(LATE, 0L)  / 3) + calculateResult.get(ABSENCE);
        return Arrays.stream(CrewStatus.values())
            .filter(status -> status.condition.test(statusDecisionValue))
            .findFirst()
            .orElse(NORMAL);
    }

    public String getStatusName() {
        return statusName;
    }
}
