package model;

import java.util.Map;

public class AttendanceStatistics {
    private final AttendanceHistory target;

    //TODO : 컴포지션 - AttendanceHistory를 넣을까?

    public AttendanceStatistics(AttendanceHistory target) {
        this.target = target;
    }

    public Map<AttendanceStatus, Integer> calculateStatusCountUntilBefore(int requestDate) {
        return null;
    }
}
