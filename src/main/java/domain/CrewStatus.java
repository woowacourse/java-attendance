package domain;

import exception.InvalidAbsenceCountException;

import java.util.Arrays;
import java.util.Comparator;

public enum CrewStatus {
    NORMAL(null, 0),
    WARNING("경고", 2),
    CONSULTANT("면담", 3),
    DISENROLLMENT("제적", 6),
    ;

    private final String expression;
    private final int absenceLowerBound;

    CrewStatus(String expression, int absenceLowerBound) {
        this.expression = expression;
        this.absenceLowerBound = absenceLowerBound;
    }

    public static CrewStatus from(final int absenceCount) {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(CrewStatus::getAbsenceLowerBound).reversed())
                .filter(value -> absenceCount >= value.getAbsenceLowerBound())
                .findFirst()
                .orElseThrow(InvalidAbsenceCountException::new);
    }

    public int getAbsenceLowerBound() {
        return absenceLowerBound;
    }

    public String getExpression() {
        return expression;
    }
}
