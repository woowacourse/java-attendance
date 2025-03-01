package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CrewHistory {

    private final Map<LocalDate, LocalDateTime> history;

    public CrewHistory(final Map<LocalDate, LocalDateTime> history) {
        this.history = new HashMap<>(history);
    }

    public void add(final LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = LocalDate.from(attendanceDateTime);
        validateNotExists(attendanceDate);
        history.put(attendanceDate, attendanceDateTime);
    }

    public void validateNotExists(final LocalDate attendanceDate) {
        if (history.containsKey(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final CrewHistory that)) {
            return false;
        }
        return Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(history);
    }
}
