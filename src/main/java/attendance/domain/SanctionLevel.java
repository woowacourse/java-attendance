package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

import attendance.exception.AttendanceArgumentException;
import attendance.utill.EnumTextConverter;

public enum SanctionLevel implements Displaier {
    DISMISS(weight -> weight > 5),
    NEED_MEETING(weight -> weight >= 3),
    WARNING(weight -> weight > 1),
    NONE(weight -> weight <= 1),
    ;

    public static final String WEIGHT_BE_POSITIVE = "가중치는 음수가 될 수 없습니다: ";
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
            throw new AttendanceArgumentException(WEIGHT_BE_POSITIVE + wight);
        }
    }

    @Override
    public String convertMessage() {
        return EnumTextConverter.convertLevel(this);
    }
}
