package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceStatistics {
    private final AttendanceHistory target;

    //TODO : 컴포지션 - AttendanceHistory를 넣을까?

    public AttendanceStatistics(AttendanceHistory target) {
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

    public String calculatePenaltyUntilBefore(LocalDate requestDate) {
        Map<AttendanceStatus, Integer> statusStatistics = calculateStatusCountUntilBefore(requestDate);
        int lateCount = statusStatistics.get(AttendanceStatus.LATE);
        int absenceCount = statusStatistics.get(AttendanceStatus.ABSENCE);
        absenceCount += (lateCount / 3);
        if (absenceCount >= 6) {
            return "제적";
        }
        if (absenceCount >= 3) {
            return "면담";
        }
        if (absenceCount >= 2) {
            return "경고";
        }
        return "없음";
    }
}
