package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum SanctionLevel {
    DISMISS("제적", weight -> weight > 5),
    NEED_MEETING("면담", weight -> weight >= 3),
    WARNING("경고", weight -> weight > 1),
    NONE("", weight -> weight <= 1),
    ;

    private final Predicate<Integer> condition;
    private final String value;

    SanctionLevel(String value, Predicate<Integer> condition) {
        this.value = value;
        this.condition = condition;
    }

    public String getValues() {
        return value;
    }

    public boolean matches(int wight) {
        return condition.test(wight);
    }

    public static SanctionLevel getValueByWight(int wight) {
        return Arrays.stream(values())
            .filter(status -> status.matches(wight))
            .findFirst()
            .orElse(NONE);
    }
}
