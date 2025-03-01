package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CrewHistories {

    private final Map<Nickname, CrewHistory> histories;

    public CrewHistories(final Map<Nickname, CrewHistory> histories) {
        this.histories = histories;
    }

    public void addHistory(final Nickname nickname, final LocalDateTime attendanceDateTime) {
        createIfNotExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.add(attendanceDateTime);
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
