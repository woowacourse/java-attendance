package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record StatusStatistics(Nickname nickname, Map<AttendanceStatus, Integer> statistics)
    implements Comparable<StatusStatistics> {
    private static final int WEIGHT_DIVIDER_FOR_LATE = 3;

    public StatusStatistics(Nickname nickname) {
        this(nickname, new HashMap<>());
    }

    public void update(Map<LocalDate, Attendance> attendances) {
        for (LocalDate date : attendances.keySet()) {
            put(attendances.get(date).state());
        }
    }

    public void put(AttendanceStatus state) {
        statistics.put(state, this.getCount(state) + 1);
    }

    public int getCount(AttendanceStatus state) {
        return statistics.getOrDefault(state, 0);
    }

    public int getWeight() {
        return getCount(AttendanceStatus.LATE) / WEIGHT_DIVIDER_FOR_LATE
            + getCount(AttendanceStatus.ABSENCE);
    }

    public int compareWeight() {
        return getCount(AttendanceStatus.LATE) + getCount(AttendanceStatus.ABSENCE);
    }

    @Override
    public int compareTo(StatusStatistics o) {
        var sanctionLevel = SanctionLevel.matchLevel(getWeight());
        var otherSanctionLevel = SanctionLevel.matchLevel(o.getWeight());

        return otherSanctionLevel.compareTo(sanctionLevel);
    }
}
