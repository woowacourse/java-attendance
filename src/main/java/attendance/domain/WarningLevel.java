package attendance.domain;

import java.util.Arrays;
import java.util.function.Function;

public enum WarningLevel {
    NONE(totalAbsent -> totalAbsent < 2),
    WARNING(totalAbsent -> totalAbsent == 2),
    INTERVIEW(totalAbsent -> 3 <= totalAbsent && totalAbsent <= 5),
    WEEDING(totalAbsent -> totalAbsent > 5);

    private final Function<Integer, Boolean> isMatch;

    WarningLevel(Function<Integer, Boolean> isMatch) {
        this.isMatch = isMatch;
    }

    public static WarningLevel from(int totalAbsent) {
        return Arrays.stream(values())
                .filter(warningLevel -> warningLevel.isMatch.apply(totalAbsent))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 경고 레벨이 없습니다."));
    }
}
