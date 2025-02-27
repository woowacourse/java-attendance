package attendance.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum Risk {
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    EXPULSION("제적", 6),
    ;

    private final String name;
    private final int minAbsenceCount;

    Risk(String name, int minAbsenceCount) {
        this.name = name;
        this.minAbsenceCount = minAbsenceCount;
    }

    public static Risk of(Map<AttendanceStatus, Integer> statistics) {
        int absenceWeight = AttendanceStatus.getConvertedAbsence(statistics);

        return Arrays.stream(values())
            .filter(risk -> risk.minAbsenceCount <= absenceWeight)
            .max(Comparator.comparing(risk -> risk.minAbsenceCount))
            .orElse(null);
    }

    public String getName() {
        return name;
    }
}
