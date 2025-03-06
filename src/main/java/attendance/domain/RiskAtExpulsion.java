package attendance.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum RiskAtExpulsion {

    NOT_APPLICABLE(0), WARNING(2), INTERVIEW(3), EXPULSION(6);

    private static final List<RiskAtExpulsion> SORTED_VALUES = Arrays.stream(RiskAtExpulsion.values())
            .sorted(Comparator.comparingInt(RiskAtExpulsion::getThreshold).reversed())
            .toList();

    private final int threshold;

    RiskAtExpulsion(final int threshold) {
        this.threshold = threshold;
    }

    public static RiskAtExpulsion of(final int absentCount, final int lateCount) {
        int totalAbsentCount = absentCount + lateCount / 3;
        return SORTED_VALUES.stream()
                .filter(riskAtExpulsion -> riskAtExpulsion.threshold <= totalAbsentCount)
                .findAny()
                .orElse(NOT_APPLICABLE);
    }

    public int getThreshold() {
        return threshold;
    }
}
