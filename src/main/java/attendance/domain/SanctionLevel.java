package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

import attendance.utility.EnumTextConverter;

public enum SanctionLevel implements Displayable {
    DISMISS(weight -> weight > 5),
    NEED_MEETING(weight -> weight >= 3),
    WARNING(weight -> weight > 1),
    NONE(weight -> weight <= 1),
    ;

    private final Predicate<Integer> condition;

    SanctionLevel(Predicate<Integer> condition) {
        this.condition = condition;
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

    @Override
    public String getConvertedText() {
        return EnumTextConverter.convertLevel(this);
    }
}
