package model;

import java.util.List;
import java.util.Objects;

public class AttendanceStatistics {
    private final List<AttendanceStatistic> statistics;

    public AttendanceStatistics(List<AttendanceStatistic> statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceStatistics that = (AttendanceStatistics) object;
        return Objects.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statistics);
    }

    public List<AttendanceStatistic> findPenaltyTargets() {
        return null;
    }
}
