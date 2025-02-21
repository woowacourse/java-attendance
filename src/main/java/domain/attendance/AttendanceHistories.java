package domain.attendance;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;
import java.util.LinkedList;
import java.util.List;

public class AttendanceHistories {
    private final LinkedList<AttendanceHistory> attendanceHistories;

    public AttendanceHistories() {
        this.attendanceHistories = new LinkedList<>();
    }

    public void add(Crew crew, AttendanceDateTime attendanceDateTime) {
        attendanceHistories.add(AttendanceHistory.of(crew, attendanceDateTime));
    }

    public void update(AttendanceHistory registeredHistory, AttendanceHistory newHistory) {
        int foundIndex = attendanceHistories.indexOf(registeredHistory);
        attendanceHistories.set(foundIndex, newHistory);
    }

    public AttendanceHistory findByCrewAndDay(Crew crew, int day) {
        return attendanceHistories.stream()
                .filter(history -> history.hasSameDay(day) && history.aboutSameCrew(crew))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 출석 기록이 존재하지 않습니다."));
    }

    public List<AttendanceHistory> findHistoriesBefore(Crew crew, int day) {
        return attendanceHistories.stream()
                .filter(history -> history.isPastHistory(day) && history.isSameCrew(crew))
                .toList();
    }
}
