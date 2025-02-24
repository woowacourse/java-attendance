package attendance.domain;

import java.util.Arrays;

public enum ExpulsionStatus {

    EXPULSION("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("없음", 0);

    private final String text;
    private final int standard;

    ExpulsionStatus(final String text, final int standard) {
        this.text = text;
        this.standard = standard;
    }

    public static ExpulsionStatus findByAbsentCount(int absentCount) {
        return Arrays.stream(values())
                .filter(status -> status.standard <= absentCount)
                .findAny()
                .orElse(NONE);
    }

    public String getText() {
        return text;
    }

}
