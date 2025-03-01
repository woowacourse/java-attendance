package attendance.domain;

import static attendance.domain.AttendanceType.*;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum CrewStatus {
    WARING("경고", value -> value >= 2 && value < 3),
    INTERVIEW("면담", value -> value >= 3 && value < 5),
    FIRE("제적", value -> value >= 5),
    NORMAL("통과", value -> value < 2);

    private final String statusName;
    private final Predicate<Integer> condition;

    CrewStatus(String statusName, Predicate<Integer> condition) {
        this.statusName = statusName;
        this.condition = condition;
    }

    public static CrewStatus calculate(Map<AttendanceType, Integer> attendanceResult) {
        int statusDecisionValue = (attendanceResult.get(LATE) / 3) + attendanceResult.get(ABSENCE);
        return Arrays.stream(CrewStatus.values())
            .filter(status -> status.condition.test(statusDecisionValue))
            .findFirst()
            .orElse(NORMAL);
    }

    public String getStatusName() {
        return statusName;
    }
}
