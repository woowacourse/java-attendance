package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceStatistic {
    private final AttendanceHistory target;

    //TODO : 컴포지션 - AttendanceHistory를 넣을까?

    public AttendanceStatistic(AttendanceHistory target) {
        this.target = target;
    }

    public Map<AttendanceStatus, Integer> calculateStatusCountUntilBefore(LocalDate limitDate) {
        List<Attendance> attendances = target.sliceByDateUntilBefore(limitDate);
        Map<AttendanceStatus, Integer> statusStatistics = new HashMap<>(Map.of(
                AttendanceStatus.NORMAL, 0,
                AttendanceStatus.LATE, 0,
                AttendanceStatus.ABSENCE, 0
        ));
        for (Attendance attendance : attendances) {
            AttendanceStatus attendanceStatus = attendance.findStatus();
            statusStatistics.replace(attendanceStatus, statusStatistics.get(attendanceStatus) + 1);
        }
        return statusStatistics;
    }

    public PenaltyStatus calculatePenaltyUntilBefore(LocalDate requestDate) {
        Map<AttendanceStatus, Integer> statusStatistics = calculateStatusCountUntilBefore(requestDate);
        int lateCount = statusStatistics.get(AttendanceStatus.LATE);
        int absenceCount = statusStatistics.get(AttendanceStatus.ABSENCE);
        return PenaltyStatus.findByAttendanceCount(lateCount, absenceCount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceStatistic that = (AttendanceStatistic) object;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(target);
    }

    public AttendanceHistory getAttendanceHistory() {
        return null;
    }
}
