package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceStatistic {
    //TODO : 서로 다른 크루객체끼리도 겹칠 확률 있음. + 안에가 자료구조라 실수로 바뀔 수 있음 -> 논리적동치성 하용하지 말자
    private final Map<AttendanceStatus, Integer> attendanceCount;
    private final PenaltyStatus penaltyStatus;

    public static AttendanceStatistic from(List<Attendance> target) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = calculateStatusCount(target);
        PenaltyStatus penaltyStatus = calculatePenaltyStatus(attendanceStatusCount);
        return new AttendanceStatistic(attendanceStatusCount, penaltyStatus);
    }

    public AttendanceStatistic(Map<AttendanceStatus, Integer> attendanceCount, PenaltyStatus penaltyStatus) {
        this.attendanceCount = attendanceCount;
        this.penaltyStatus = penaltyStatus;
    }

    private static Map<AttendanceStatus, Integer> calculateStatusCount(List<Attendance> target) {
        Map<AttendanceStatus, Integer> statusStatistics = new HashMap<>(Map.of(
                AttendanceStatus.NORMAL, 0,
                AttendanceStatus.LATE, 0,
                AttendanceStatus.ABSENCE, 0
        ));
        for (Attendance attendance : target) {
            AttendanceStatus attendanceStatus = attendance.findStatus();
            statusStatistics.replace(attendanceStatus, statusStatistics.get(attendanceStatus) + 1);
        }
        return statusStatistics;
    }

    private static PenaltyStatus calculatePenaltyStatus(Map<AttendanceStatus, Integer> target) {
        int lateCount = target.get(AttendanceStatus.LATE);
        int absenceCount = target.get(AttendanceStatus.ABSENCE);
        return PenaltyStatus.findByAttendanceCount(lateCount, absenceCount);
    }

    public Map<AttendanceStatus, Integer> getAttendanceCount() {
        return Collections.unmodifiableMap(attendanceCount);
    }

    public PenaltyStatus getPenaltyStatus() {
        return penaltyStatus;
    }
}
