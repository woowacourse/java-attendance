package attendance.domain;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public record StatusStatistics(Nickname nickname, EnumMap<AttendanceStatus, Integer> statistics)
    implements Comparable<StatusStatistics> {
    private static final int WEIGHT_DIVIDER_FOR_LATE = 3;

    public StatusStatistics(Nickname nickname) {
        this(nickname, new EnumMap<>(AttendanceStatus.class));
    }

    public void update(Map<LocalDate, AttendanceStatus> attendances) {
        for (LocalDate date : attendances.keySet()) {
            var state = attendances.get(date);
            statistics.put(state, this.getCount(state) + 1);
        }
    }

    public int getCount(AttendanceStatus state) {
        return statistics.getOrDefault(state, 0);
    }

    public int getWeight() {
        return getCount(AttendanceStatus.LATE) / WEIGHT_DIVIDER_FOR_LATE
            + getCount(AttendanceStatus.ABSENCE);
    }

    public String getConvertedSanctionLevel() {
        return getSanctionLevel().convert();
    }

    private int compareWeight() {
        return getCount(AttendanceStatus.LATE) + getCount(AttendanceStatus.ABSENCE);
    }

    private SanctionLevel getSanctionLevel() {
        return SanctionLevel.matchLevel(getWeight());
    }

    @Override
    public int compareTo(StatusStatistics o) {
        return Comparator.comparing(StatusStatistics::getSanctionLevel).reversed()
            .thenComparing(StatusStatistics::compareWeight)
            .thenComparing(StatusStatistics::nickname)
            .compare(this, o);
    }

}
