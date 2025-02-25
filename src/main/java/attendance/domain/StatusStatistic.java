package attendance.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

import attendance.domain.attendance.AttendanceList;

public class StatusStatistic implements Comparable<StatusStatistic> {
    private static final String FORMAT_STATE = "%s: %d회\n";
    private static final String FORMAT_SANCTION = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    public static final int DIVIDER = 3;

    private final Map<AttendanceStatus, Integer> statistic;
    private final String nickname;

    public static StatusStatistic of(AttendanceList attendanceList, String nickName) {
        return new StatusStatistic(attendanceList.produceStatistic(), nickName);
    }

    public StatusStatistic(Map<AttendanceStatus, Integer> statistic, String nickname) {
        this.statistic = statistic;
        this.nickname = nickname;
    }

    public String getReportDetail() {
        var stringBuilder = new StringBuilder();
        var attendanceStatusList = statistic.keySet().stream().sorted().toList();
        for (AttendanceStatus attendanceStatus : attendanceStatusList) {
            String status = attendanceStatus.getValue();
            int count = statistic.get(attendanceStatus);
            var formatted = String.format(FORMAT_STATE, status, count);
            stringBuilder.append(formatted);
        }

        return stringBuilder.toString();
    }

    public SanctionLevel judgeSanctionLevel() {
        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var weight = late / DIVIDER + absence;
        return SanctionLevel.getByWight(weight);
    }

    public String getReportSanctions() {
        var stringBuilder = new StringBuilder();

        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var formatted = String.format(FORMAT_SANCTION, nickname, absence, late, judgeSanctionLevel().value);
        stringBuilder.append(formatted);

        return stringBuilder.toString();
    }

    private String getName() {
        return nickname;
    }

    private int getWeight() {
        return statistic.getOrDefault(AttendanceStatus.ABSENCE, 0)
            + statistic.getOrDefault(AttendanceStatus.LATE, 0);
    }

    @Override
    public int compareTo(StatusStatistic o) {
        return Comparator.comparing(StatusStatistic::judgeSanctionLevel)
            .thenComparing(StatusStatistic::getWeight, Comparator.reverseOrder())
            .thenComparing(StatusStatistic::getName)
            .compare(this, o);
    }

    public enum SanctionLevel {
        DISMISS("제적", weight -> weight > 5),
        NEED_MEETING("면담", weight -> weight >= 3),
        WARNING("경고", weight -> weight > 1),
        NONE("", weight -> weight <= 1),
        ;

        public String getValues() {
            return value;
        }

        private final Predicate<Integer> condition;
        private final String value;

        SanctionLevel(String value, Predicate<Integer> condition) {
            this.value = value;
            this.condition = condition;
        }

        public boolean matches(int wight) {
            return condition.test(wight);
        }

        public static SanctionLevel getByWight(int wight) {
            return Arrays.stream(values())
                .filter(status -> status.matches(wight))
                .findFirst()
                .orElse(NONE);
        }
    }
}
