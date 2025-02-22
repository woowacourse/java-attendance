package attendance.domain;

import java.util.Arrays;

public enum Warning {
    EXPULSION(5, "제적"),
    INTERVIEW(3, "면담"),
    WARN(2, "경고"),
    NONE(0, ""),
    ;

    private final int criteria;
    private final String message;

    Warning(int criteria, String message) {
        this.criteria = criteria;
        this.message = message;
    }

    public static Warning check(final long count) {
        return Arrays.stream(Warning.values())
                .filter(warning -> count >= warning.criteria)
                .findFirst()
                .orElse(NONE);
    }

    public String getMessage() {
        return message;
    }
}
