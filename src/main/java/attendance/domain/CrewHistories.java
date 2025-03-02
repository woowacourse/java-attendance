package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CrewHistories {

    private final Map<Nickname, CrewHistory> histories;

    public CrewHistories(final Map<Nickname, CrewHistory> histories) {
        this.histories = new HashMap<>(histories);
    }

    public void addHistory(final Nickname nickname, final LocalDateTime attendanceDateTime) {
        createIfNotExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.add(attendanceDateTime);
    }

    public void validateHistoryNotExists(final Nickname nickname, final LocalDate attendanceDate) {
        validateKeyExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.validateNotExists(attendanceDate);
    }

    public void validateKeyExists(final Nickname nickname) {
        if (!histories.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void modify(final Nickname nickname, final LocalDateTime modifyingDateTime) {
        validateKeyExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.modify(modifyingDateTime);
    }

    private void createIfNotExists(final Nickname nickname) {
        if (!histories.containsKey(nickname)) {
            histories.put(nickname, new CrewHistory(new HashMap<>()));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final CrewHistories that)) {
            return false;
        }
        return Objects.equals(histories, that.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(histories);
    }
}
