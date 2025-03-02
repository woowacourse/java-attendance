package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

import attendance.exception.AttendanceArgumentException;

public enum SanctionLevel {
    DISMISS(weight -> weight > 5),
    NEED_MEETING(weight -> weight >= 3),
    WARNING(weight -> weight > 1),
    NONE(weight -> weight <= 1),
    ;

    private final Predicate<Integer> condition;

    SanctionLevel(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static SanctionLevel matchLevel(int wight) {
        validateWeight(wight);
        return Arrays.stream(values())
            .filter(status -> status.matches(wight))
            .findFirst()
            .orElse(NONE);
    }

    private boolean matches(int wight) {
        return condition.test(wight);
    }

    private static void validateWeight(int wight) {
        if (wight < 0) {
            throw new AttendanceArgumentException("가중치는 음수가 될 수 없습니다: " + wight);
        }
    }
}
