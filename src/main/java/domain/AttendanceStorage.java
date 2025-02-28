package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceStorage {
    private final Set<Crew> crews;
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceStorage() {
        this.crews = new HashSet<>();
        this.attendanceHistories = new ArrayList<>();
    }

    public AttendanceHistory getAttendanceHistory(int index) {
        return attendanceHistories.get(index);
    }

    public List<AttendanceHistory> getAllHistoriesOf(Crew crew, int untilDay) {
        return attendanceHistories.stream()
                .filter(history -> history.hasSameCrew(crew) &&
                        history.isBeforeFrom(untilDay))
                .toList();
    }

    public Set<Crew> getCrews() {
        return new HashSet<>(crews);
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public void add(AttendanceHistory attendanceHistory) {
        attendanceHistories.add(attendanceHistory);
    }

    public void replace(AttendanceHistory newHistory) {
        int replaceIndex = indexOfSameDateAndCrew(newHistory);
        attendanceHistories.set(replaceIndex, newHistory);
    }

    public boolean containsSameNickname(String nickname) {
        return crews.contains(Crew.from(nickname));
    }

    public boolean containsSameHistoryOf(Crew crew, LocalDateTime dateTime) {
        return attendanceHistories.stream()
                .anyMatch(history -> history.hasSameCrew(crew) && history.hasSameDate(dateTime));
    }

    public int indexOfSameDateAndCrew(AttendanceHistory attendanceHistory) {
        AttendanceHistory foundHistory = attendanceHistories.stream()
                .filter(comparedHistory -> comparedHistory.hasSameCrew(attendanceHistory)
                        && comparedHistory.hasSameDate(attendanceHistory))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return attendanceHistories.indexOf(foundHistory);
    }
}
