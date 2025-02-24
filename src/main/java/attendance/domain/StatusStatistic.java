package attendance.domain;

import java.util.Map;

import attendance.domain.attendance.AttendanceList;

public class StatusStatistic implements Comparable<StatusStatistic> {
    private static final String FORMAT_STATE = "%s: %d회\n";
    private static final String FORMAT_SANCTION = "- %s: 결석 %d회, 지각 %d회 (%s)\n";

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
            String status = attendanceStatus.getStatus();
            int count = statistic.get(attendanceStatus);
            var formatted = String.format(FORMAT_STATE, status, count);
            stringBuilder.append(formatted);
        }

        return stringBuilder.toString();
    }

    public SanctionLevel judgeSanctionLevel() {
        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var weight = late / 3 + absence;
        return calculateSanctionLevel(weight);
    }

    private SanctionLevel calculateSanctionLevel(int weight) {
        if (weight > 5) {
            return SanctionLevel.DISMISS;
        }
        if (weight >= 3) {
            return SanctionLevel.NEED_MEETING;
        }
        if (weight > 1) {
            return SanctionLevel.WARNING;
        }
        return SanctionLevel.NONE;
    }

    public String getReportSanctions() {
        var stringBuilder = new StringBuilder();

        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var formatted = String.format(FORMAT_SANCTION, nickname, absence, late, judgeSanctionLevel().value);
        stringBuilder.append(formatted);

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(StatusStatistic o) {
        SanctionLevel sanctionLevel = judgeSanctionLevel();
        SanctionLevel otherSanctionLevel = o.judgeSanctionLevel();
        if (sanctionLevel == otherSanctionLevel) {
            return compareToWeight(o);
        }
        return sanctionLevel.compareTo(otherSanctionLevel);
    }

    private int compareToWeight(StatusStatistic o) {
        int weight = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0)
            + statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int otherWeight = o.statistic.getOrDefault(AttendanceStatus.ABSENCE, 0)
            + o.statistic.getOrDefault(AttendanceStatus.LATE, 0);

        if (weight == otherWeight) {
            return nickname.compareTo(o.nickname);
        }

        return otherWeight - weight;
    }

    public enum SanctionLevel {
        DISMISS("제적"),
        NEED_MEETING("면담"),
        WARNING("경고"),
        NONE(""),
        ;

        public String getValues() {
            return value;
        }

        private final String value;

        SanctionLevel(String value) {
            this.value = value;
        }
    }
}
