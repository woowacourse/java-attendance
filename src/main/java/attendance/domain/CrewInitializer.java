package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CrewInitializer {

    private final Map<String, List<LocalDateTime>> attendances;

    public CrewInitializer(final Map<String, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    public CrewHistories initialize() {
        CrewHistories crewHistories = new CrewHistories(new HashMap<>());
        for (Entry<String, List<LocalDateTime>> entry : attendances.entrySet()) {
            Nickname nickname = new Nickname(entry.getKey());
            addHistory(entry, crewHistories, nickname);
        }
        return crewHistories;
    }

    private void addHistory(final Entry<String, List<LocalDateTime>> entry, final CrewHistories crewHistories,
                           final Nickname nickname) {
        for (LocalDateTime attendanceTime : entry.getValue()) {
            crewHistories.addHistory(nickname, attendanceTime);
        }
    }
}
