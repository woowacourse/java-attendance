package domain;

import exception.InvalidAbsenceCountException;

import java.util.Arrays;
import java.util.Comparator;

public enum CrewStatus {
    NORMAL(0),
    WARNING(2),
    CONSULTANT(3),
    DISENROLLMENT(6),
    ;

    private final int absenceLowerBound;

    CrewStatus(int absenceLowerBound) {
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
}
