package domain;

import java.util.LinkedList;

public class AttendanceHistories {
    // TODO: LinkedList 로 변경 고민해보기
    private final LinkedList<AttendanceHistory> attendanceHistories;

    public AttendanceHistories() {
        this.attendanceHistories = new LinkedList<>();
    }

    public void add(Crew crew, AttendanceDateTime attendanceDateTime) {
        attendanceHistories.add(AttendanceHistory.of(crew, attendanceDateTime));
    }

}
