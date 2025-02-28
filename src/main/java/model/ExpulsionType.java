package model;

import java.util.Arrays;

public enum ExpulsionType {

    EXPULSION("제적", 5),
    INTERVIEW("면담", 4),
    WARNING("경고", 3),
    NONE("없음", 0);

    private static final int ABSENCE_RATIO = 3;

    private final String displayName;
    private final int discriminationLimitCount;


    ExpulsionType(final String displayName, final int discriminationLimitCount) {
        this.displayName = displayName;
        this.discriminationLimitCount = discriminationLimitCount;
    }

    public static ExpulsionType find(final AttendanceCountDto dto) {
        final int discriminationCount = calculateDiscriminationCount(dto);

        return Arrays.stream(values())
                .filter(o -> o.discriminationLimitCount < discriminationCount)
                .findFirst()
                .orElse(NONE);
    }

    public String getDisplayName() {
        return displayName;
    }

    private static int calculateDiscriminationCount(final AttendanceCountDto dto) {
        final int absenceCount = dto.absenceCount();
        final int tardinessCount = dto.tardinessCount();

        return absenceCount + tardinessCount / ABSENCE_RATIO;
    }
}
