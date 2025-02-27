package domain;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public enum DisciplinaryStatus {
    EXPULSION(5),
    MEETING(2),
    WARNING(1),
    NONE(0);

    private static final int LATENESS_TO_ABSENCE_RATIO = 3;
    private static final NavigableMap<Integer, DisciplinaryStatus> THRESHOLD_MAP;

    private final int absenceCount;

    static {
        final TreeMap<Integer, DisciplinaryStatus> map = new TreeMap<>();
        for (final DisciplinaryStatus status : values()) {
            map.put(status.absenceCount, status);
        }
        THRESHOLD_MAP = Collections.unmodifiableNavigableMap(map);
    }

    DisciplinaryStatus(final int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static DisciplinaryStatus findByAbsenceAndLatenessCount(final int inputAbsenceCount,
                                                                   final int latenessCount) {
        final int adjustedAbsenceCount = calculateAdjustedAbsenceCount(inputAbsenceCount, latenessCount);
        final Map.Entry<Integer, DisciplinaryStatus> entry = THRESHOLD_MAP.lowerEntry(adjustedAbsenceCount);

        if (entry != null) {
            return entry.getValue();
        }
        return NONE;
    }

    private static int calculateAdjustedAbsenceCount(final int inputAbsenceCount, final int latenessCount) {
        return (latenessCount / LATENESS_TO_ABSENCE_RATIO) + inputAbsenceCount;
    }
}
