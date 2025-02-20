package domain;

import java.util.LinkedList;
import java.util.List;

public class AttendanceHistories {
    // TODO: LinkedList 로 변경 고민해보기
    private final LinkedList<AttendanceHistory> attendanceHistories;

    public AttendanceHistories() {
        this.attendanceHistories = new LinkedList<>();
    }

    public void add(Crew crew, AttendanceDateTime attendanceDateTime) {
        attendanceHistories.add(AttendanceHistory.of(crew, attendanceDateTime));
    }

    public void update(AttendanceHistory beforeAttendanceHistory, AttendanceHistory newAttendanceHistory) {
        int foundIndex = attendanceHistories.indexOf(beforeAttendanceHistory);
        attendanceHistories.set(foundIndex, newAttendanceHistory);
    }

    public AttendanceHistory findHistoryBy(AttendanceHistory newAttendanceHistory) {
        return attendanceHistories.stream()
                .filter(history -> history.hasSameDay(newAttendanceHistory) && history.isSameCrew(newAttendanceHistory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 출석 기록이 존재하지 않습니다."));
    }

    public List<AttendanceHistory> findAllHistoriesOf(Crew crew) {
        return attendanceHistories.stream()
                .filter(history -> history.isSameCrew(crew))
                .toList();
    }

    public List<AttendanceHistory> findHistoriesBefore(Crew crew, int day) {
        return attendanceHistories.stream()
                .filter(history -> history.isPastHistory(day) && history.isSameCrew(crew))
                .toList();
    }
}
