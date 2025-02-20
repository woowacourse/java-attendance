package domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceTypeCount {

    private final Map<AttendanceType, Integer> attendanceTypeCount;

    private AttendanceTypeCount(Map<AttendanceType, Integer> attendanceTypeCount) {
        this.attendanceTypeCount = attendanceTypeCount;
    }

    public static AttendanceTypeCount from(int day, List<AttendanceHistory> attendanceHistories) {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        Map<Integer, AttendanceHistory> historyOfDay = new HashMap<>();

        for (AttendanceHistory history : attendanceHistories) {
            historyOfDay.put(history.getDay(), history);
        }

        for (int currentDay = 1; currentDay < day; currentDay++) {
            if (AttendanceDate.isRestDay(currentDay)) {
                continue;
            }

            if (!historyOfDay.containsKey(currentDay)) {
                attendanceTypeCount.merge(AttendanceType.ABSENCE, 1, Integer::sum);
                continue;
            }

            AttendanceDateTime dateTimeOfDay = historyOfDay.get(currentDay).getAttendanceDateTime();
            attendanceTypeCount.merge(dateTimeOfDay.getAttendanceType(), 1, Integer::sum);
        }

        return new AttendanceTypeCount(attendanceTypeCount);
    }

    public int getTotalAbsenceCount() {
        return getConsideredAbsenceCountFromLate() + getAbsenceCount();
    }

    private int getConsideredAbsenceCountFromLate() {
        int lateCount = attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0);
        return lateCount / 3;
    }

    public int getAttendanceCount() {
        return attendanceTypeCount.getOrDefault(AttendanceType.PRESENT, 0);
    }

    public int getAbsenceCount() {
        return attendanceTypeCount.getOrDefault(AttendanceType.ABSENCE, 0);
    }


    public int getLateCount() {
        return attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0);
    }
}
