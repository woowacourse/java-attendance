import java.util.Map;

public class AttendanceStatistic {
    private final Map<AttendanceStatus, Integer> statistic;

    public AttendanceStatistic(Map<AttendanceStatus, Integer> statistic) {
        this.statistic = statistic;
    }

    public int getAttendanceCount() {
        return statistic.getOrDefault(AttendanceStatus.ATTENDANCE, 0);
    }

    public int getLateCount() {
        return statistic.getOrDefault(AttendanceStatus.LATE, 0);
    }

    public int getAbsenceCount() {
        return statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
    }
}
