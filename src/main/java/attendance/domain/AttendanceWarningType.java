package attendance.domain;

import java.util.Arrays;

public enum AttendanceWarningType {
    EXPULSION("제적", 6), //TODO: 이름변경
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    NONE("해당없음", 0);

    private final String name;
    private final int score;

    AttendanceWarningType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static AttendanceWarningType find(final int expulsionCount, final int late) { // TODO: 이름변경
        int allScore = expulsionCount + (late / 3);
        return Arrays.stream(AttendanceWarningType.values())
                .filter(type -> type.score <= allScore)
                .findAny()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }
}
