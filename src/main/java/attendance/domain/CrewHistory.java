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

    public void validateExists(final LocalDate attendanceDate) {
        if (!history.containsKey(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public LocalDateTime modify(final LocalDateTime modifyingDateTime) {
        LocalDate modifyDate = LocalDate.from(modifyingDateTime);
        validateExists(modifyDate);
        LocalDateTime previousDateTime = history.get(modifyDate);
        history.put(modifyDate, modifyingDateTime);
        return previousDateTime;
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
