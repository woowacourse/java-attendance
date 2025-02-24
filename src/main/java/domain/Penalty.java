package domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Penalty {
    EXPELLED("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    NONE("패스", 0);

    public final String penalty;
    public final int count;

    Penalty(String penalty, int count) {
        this.penalty = penalty;
        this.count = count;
    }

    public static Penalty of(int absenceCount, int latenessCount) {
        int totalAbsences = absenceCount + latenessCount / 3;
        return Arrays.stream(values())
            .filter(penalty -> totalAbsences >= penalty.count)
            .findFirst()
            .orElse(NONE);
    }

    public static Map<String, StatisticsResult> calculateExpelledWarning
        (LocalDate nowDate, Map<String, Crew> crews) {
        return crews.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(),
                AttendanceStatus.countStatus(nowDate, entry.getValue())))
            .filter(entry -> entry.getValue().hasPenalty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a, b) -> b, LinkedHashMap::new));
    }
}
