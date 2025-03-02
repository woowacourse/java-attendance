package model;

import java.util.Arrays;

public enum ExpulsionType {

    EXPULSION("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    NONE("없음", 0);

    private static final int ABSENCE_RATIO = 3;

    private final String displayName;
    private final int discriminationLimitCount;


    ExpulsionType(final String displayName, final int discriminationLimitCount) {
        this.displayName = displayName;
        this.discriminationLimitCount = discriminationLimitCount;
    }

    public static ExpulsionType find(final AttendanceCountsDto dto) {
        final int discriminationCount = calculateDiscriminationCount(dto);

        return Arrays.stream(values())
                .filter(o -> o.discriminationLimitCount < discriminationCount)
                .findFirst()
                .orElse(NONE);
    }

    public String getDisplayName() {
        return displayName;
    }

    private static int calculateDiscriminationCount(final AttendanceCountsDto dto) {
        final int absenceCount = dto.map().get(AttendanceStatus.ABSENCE);
        final int tardinessCount = dto.map().get(AttendanceStatus.TARDINESS);

        return absenceCount + tardinessCount / ABSENCE_RATIO;
    }
}
