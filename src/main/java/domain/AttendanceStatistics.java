package domain;

import static domain.Attendances.LATE_WEIGHT;

public record AttendanceStatistics(int present, int late, int absent, AlertCode alertCode) implements
        Comparable<AttendanceStatistics> {

    @Override
    public int compareTo(AttendanceStatistics o) {
        int myCriteria = absent * LATE_WEIGHT + late;
        int otherCriteria = o.absent * LATE_WEIGHT + o.late;
        if (myCriteria > otherCriteria) {
            return -1;
        } else if (myCriteria < otherCriteria) {
            return 1;
        }
        return 0;
    }
}
