package domain;

public record AttendanceStatistics(int present, int late, int absent, AlertCode alertCode) implements
        Comparable<AttendanceStatistics> {


    @Override
    public int compareTo(AttendanceStatistics o) {
        int myCriteria = absent * 3 + late;
        int otherCriteria = o.absent * 3 + o.late;
        if (myCriteria > otherCriteria) {
            return -1;
        } else if (myCriteria < otherCriteria) {
            return 1;
        }
        return 0;
    }
}
