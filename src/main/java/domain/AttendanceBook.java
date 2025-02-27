package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
            throw new IllegalStateException();
        }
    }

    public AttendanceHistory findByCrew(final Crew crew) {
        validateContainsCrew(crew);
        return attendanceBook.get(crew);
    }

    private void validateContainsCrew(final Crew crew) {
        if (!attendanceBook.containsKey(crew)) {
            throw new IllegalArgumentException();
        }
    }

    public Map<Crew, AttendanceHistory> calculateRiskOfExpulsionCrews(final LocalDate targetDate) {
        return attendanceBook.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isRiskOfExpulsion(targetDate))
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue()
                ));
    }
}
