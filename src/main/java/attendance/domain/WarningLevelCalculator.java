package attendance.domain;

import static attendance.domain.WarningLevel.COUNSELING;
import static attendance.domain.WarningLevel.NONE;
import static attendance.domain.WarningLevel.REMOVE;
import static attendance.domain.WarningLevel.WARNING;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class WarningLevelCalculator {
    private static final Map<Predicate<Integer>, Supplier<WarningLevel>> CALCULATE_LEVEL;
    private static final int REMOVE_ABSENCE_BOUND = 6;
    private static final int COUNSELING_ABSENCE_BOUND = 3;
    private static final int WARNING_ABSENCE_BOUND = 2;
    private static final int NONE_ABSENCE_BOUND = 0;

    static {
        CALCULATE_LEVEL = new LinkedHashMap<>();

        CALCULATE_LEVEL.put(absenceCountPredicate(REMOVE_ABSENCE_BOUND), () -> REMOVE);
        CALCULATE_LEVEL.put(absenceCountPredicate(COUNSELING_ABSENCE_BOUND), () -> COUNSELING);
        CALCULATE_LEVEL.put(absenceCountPredicate(WARNING_ABSENCE_BOUND), () -> WARNING);
        CALCULATE_LEVEL.put(absenceCountPredicate(NONE_ABSENCE_BOUND), () -> NONE);
    }

    private static Predicate<Integer> absenceCountPredicate(int absenceCount){
        return count -> count >= absenceCount;
    }

    public static WarningLevel calculateLevel(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        return CALCULATE_LEVEL.entrySet().stream()
                .filter(entry -> entry.getKey().test(calculateTotalAbsence(attendanceStatuses)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결석 정보가 올바르지 않습니다."))
                .getValue()
                .get();
    }

    private static int calculateTotalAbsence(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        return attendanceStatuses.get(AttendanceStatus.ABSENCE) + convertLateness(
                attendanceStatuses.get(AttendanceStatus.LATENESS));
    }


    private static int convertLateness(int latenessCount) {
        return latenessCount / 3;
    }
}
