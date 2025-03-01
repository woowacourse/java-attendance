package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, AttendanceHistory> attendanceBook;

    public AttendanceBook() {
        this.attendanceBook = new HashMap<>();
    }

    public void registerCrew(final Crew crew) {
        validateAlreadyRegister(crew);
        attendanceBook.put(crew, new AttendanceHistory(crew));
    }

    public boolean containsCrew(final Crew crew) {
        return attendanceBook.containsKey(crew);
    }

    private void validateAlreadyRegister(final Crew crew) {
        if (attendanceBook.containsKey(crew)) {
            throw new IllegalStateException("오늘 이미 출석한 크루입니다.");
        }
    }

    public AttendanceHistory findByCrew(final Crew crew) {
        validateContainsCrew(crew);
        return attendanceBook.get(crew);
    }

    private void validateContainsCrew(final Crew crew) {
        if (!attendanceBook.containsKey(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    public List<AttendanceHistory> calculateRiskOfExpulsionHistory(final LocalDate targetDate) {
        return attendanceBook.entrySet()
                .stream()
                .map(Entry::getValue)
                .filter(attendanceHistory -> attendanceHistory.isRiskOfExpulsion(targetDate))
                .collect(Collectors.toList());
    }
}
