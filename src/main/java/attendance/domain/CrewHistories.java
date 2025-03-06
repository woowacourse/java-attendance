package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

public class CrewHistories {

    private final Map<String, CrewHistory> histories;

    public CrewHistories(final Map<String, CrewHistory> histories) {
        this.histories = new HashMap<>(histories);
    }

    public void addHistory(final String nickname, final LocalDateTime attendanceDateTime) {
        createIfNotExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.add(attendanceDateTime);
    }

    public void validateHistoryNotExists(final String nickname, final LocalDate attendanceDate) {
        validateKeyExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.validateNotExists(attendanceDate);
    }

    public void validateHistoryExists(final String nickname, final LocalDate attendanceDate) {
        validateKeyExists(nickname);
        CrewHistory crewHistory = histories.get(nickname);
        crewHistory.validateExists(attendanceDate);
    }

    public void validateKeyExists(final String nickname) {
        if (!histories.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public LocalDateTime modify(final String nickname, final LocalDateTime modifyingDateTime, final LocalDate nowDate) {
        validateKeyExists(nickname);
        validatePreviousDate(LocalDate.from(modifyingDateTime), nowDate);
        CrewHistory crewHistory = histories.get(nickname);
        return crewHistory.modify(modifyingDateTime);
    }

    public Optional<LocalDateTime> findHistoryOfDate(final String nickname, final LocalDate date) {
        CrewHistory crewHistory = histories.get(nickname);
        return crewHistory.find(date);
    }

    public CrewHistory findHistory(final String nickname) {
        validateKeyExists(nickname);
        return histories.get(nickname);
    }

    public Map<String, AttendanceCounter> makeAttendanceCounterByNickname(final LocalDate nowDate,
                                                                          final CampusScheduler campusScheduler) {
        Map<String, AttendanceCounter> result = new HashMap<>();
        for (Entry<String, CrewHistory> entry : histories.entrySet()) {
            CrewHistory history = entry.getValue();
            AttendanceCounter counter = campusScheduler.countByAttendanceState(history, nowDate);
            result.put(entry.getKey(), counter);
        }
        return result;
    }

    private void validatePreviousDate(final LocalDate date, final LocalDate nowDate) {
        if (date.equals(nowDate) || date.isAfter(nowDate)) {
            throw new IllegalArgumentException("[ERROR] 과거의 날짜만 가능합니다.");
        }
    }

    private void createIfNotExists(final String nickname) {
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
