package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

import attendance.exception.AttendanceArgumentException;
import attendance.interfaces.EnumDisplayConverter;
import attendance.interfaces.EnumToTextConverter;

public enum SanctionLevel implements EnumDisplayConverter {
    DISMISS(weight -> weight > 5),
    NEED_MEETING(weight -> weight >= 3 && weight <= 5),
    WARNING(weight -> weight == 2),
    NONE(weight -> weight == 1 || weight == 0);

    private static final String WEIGHT_BE_POSITIVE = "가중치는 음수가 될 수 없습니다: ";
    private static final String NOT_REGISTERED_CONVERTER = "컨버터가 등록되지 않았습니다.";

    private final Predicate<Integer> condition;
    private static EnumToTextConverter<SanctionLevel> enumToTextConverter;

    SanctionLevel(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static void setConverter(EnumToTextConverter<SanctionLevel> levelEnumToTextConverter) {
        enumToTextConverter = levelEnumToTextConverter;
    }

    public static SanctionLevel matchLevel(int wight) {
        validateWeight(wight);
        return Arrays.stream(values())
            .filter(status -> status.matches(wight))
            .findFirst()
            .orElse(NONE);
    }

    private static void validateWeight(int wight) {
        if (wight < 0) {
            throw new AttendanceArgumentException(WEIGHT_BE_POSITIVE + wight);
        }
    }

    private boolean matches(int wight) {
        return condition.test(wight);
    }

    @Override
    public String convert() {
        if (enumToTextConverter == null) {
            throw new IllegalStateException(NOT_REGISTERED_CONVERTER);
        }
        return enumToTextConverter.convert(this);
    }
}
