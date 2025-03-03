package domain;

import domain.policy.AttendanceStateRule;
import domain.policy.absent.AbsentRule;

import java.util.EnumMap;
import java.util.Map;

public class AttendanceCounts {

    private final Nickname nickname;
    private final Map<AttendanceStateRule, Integer> counts;

    private AttendanceCounts(Nickname nickname, Map<AttendanceStateRule, Integer> counts) {
        this.nickname = nickname;
        this.counts = new EnumMap<>(counts);
    }

    public static AttendanceCounts initialize(Nickname nickname) {
        Map<AttendanceStateRule, Integer> stateToCounts = new EnumMap<>(AttendanceStateRule.class);
        for (AttendanceStateRule rule : AttendanceStateRule.values()) {
            stateToCounts.put(rule, 0);
        }
        return new AttendanceCounts(nickname, stateToCounts);
    }

    public int getCount(AttendanceStateRule rule) {
        return counts.getOrDefault(rule, 0);
    }

    public void increment(AttendanceStateRule rule) {
        counts.put(rule, getCount(rule) + 1);
    }

    public int getAdjustedAbsentCount() {
        return AbsentRule.adjustAbsentCount(
                getCount(AttendanceStateRule.ABSENT),
                getCount(AttendanceStateRule.LATE));
    }

    public int getExpulsionRiskLevel() {
        return AbsentRule.calculateExpulsionRiskLevel(
                getCount(AttendanceStateRule.ABSENT),
                getCount(AttendanceStateRule.LATE));
    }

    public Nickname getNickname() {
        return nickname;
    }
}
