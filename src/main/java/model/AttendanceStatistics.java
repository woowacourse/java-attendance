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
        Map<AttendanceStatus, Integer> statusStatistics = new HashMap<>();
        for (Attendance attendance : attendances) {
            AttendanceStatus attendanceStatus = attendance.findStatus();
            statusStatistics.merge(attendanceStatus, 1, (countTotal, addingCount) -> countTotal + 1);
        }
        return statusStatistics;
    }

    public String calculatePenaltyUntilBefore(LocalDate requestDate) {
        return null;
    }
}
