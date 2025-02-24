package domain;

import domain.rule.AbsentRule;
import domain.rule.AttendanceStateRule;

import java.util.HashMap;
import java.util.Map;

public record AttendanceStatistics(
        String nickname,
        int attendCount,
        int lateCount,
        int absentCount
) {
    public static AttendanceStatistics from(String nickname, Map<AttendanceStateRule, Integer> format) {
        return new AttendanceStatistics(
                nickname,
                format.get(AttendanceStateRule.ATTEND),
                format.get(AttendanceStateRule.LATE),
                format.get(AttendanceStateRule.ABSENT));
    }

    public static Map<AttendanceStateRule, Integer> getFormat() {
        Map<AttendanceStateRule, Integer> statisticsFormat = new HashMap<>();

        for (AttendanceStateRule value : AttendanceStateRule.values()) {
            statisticsFormat.put(value, 0);
        }
        return statisticsFormat;
    }

    public int getAdjustedAbsentCount() {
        return absentCount + (lateCount / AbsentRule.LATE_TO_ABSENT_RATIO);
    }

    public int getExpulsionRiskLevel() {
        return absentCount * AbsentRule.LATE_TO_ABSENT_RATIO + lateCount;
    }
}
