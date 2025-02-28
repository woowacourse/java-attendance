package domain;

import java.time.LocalDate;
import java.util.Comparator;

public class AttendanceTimesComparator implements Comparator<AttendanceTimes> {
    private final LocalDate date;

    public AttendanceTimesComparator(LocalDate date) {
        this.date = date;
    }

    @Override
    public int compare(AttendanceTimes o1, AttendanceTimes o2) {
        int score1 = o1.countLateBeforeDate(date) + (o1.countAbsenceBeforeDate(date) * 3);
        int score2 = o2.countLateBeforeDate(date) + (o2.countAbsenceBeforeDate(date) * 3);

        return Integer.compare(score1, score2);
    }
}
