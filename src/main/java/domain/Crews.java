package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> histories, LocalDate standard) {
        crews = new ArrayList<>();
        for (String username : histories.keySet()) {
            Crew crew = new Crew(username, histories.get(username), standard);
            crews.add(crew);
        }
    }

    public void addHistory(String username, LocalDateTime attendanceTime) {
        Crew findCrew = getCrew(username);
        findCrew.addAttendance(attendanceTime);
    }

     public String editHistory(String username, LocalDateTime localDateTime) {
        Crew findCrew = getCrew(username);
        findCrew.editHistory(localDateTime);
        return findCrew.getHistoryResult(localDateTime);
    }

    public Map<String, Integer> getAttendanceAllResult(String username, LocalDateTime localDateTime) {
        Crew findCrew = getCrew(username);
        return findCrew.getAttendanceAllResult(localDateTime);
    }

    public List<Crew> getHighAbsenceLevelCrews(LocalDateTime localDateTime) {
        return crews.stream()
                .filter(crew -> !crew.getClassifyAbsenceLevel(localDateTime).equals(AbsenceLevel.NORMAL)).toList();
    }

    public List<History> getBeforeHistory(String username, LocalDateTime standard) {
        Crew findCrew = getCrew(username);
        return findCrew.getBeforeHistories(standard);
    }
    public String getHistoryResult(String username, LocalDateTime attendanceTime) {
        Crew findCrew = getCrew(username);
        return findCrew.getHistoryResult(attendanceTime);
    }

    public LocalDateTime getHistory(String username, LocalDateTime localDateTime) {
        Crew findCrew = getCrew(username);
        return findCrew.getHistoryDate(localDateTime);
    }

    public AbsenceLevel getClassifyAbsenceLevel(String username, LocalDateTime localDateTime) {
        Crew findCrew = getCrew(username);
        return findCrew.getClassifyAbsenceLevel(localDateTime);
    }

    private Crew getCrew(String username) {
        return crews.stream()
                .filter(crew -> crew.getUserName().equals(username)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않은 크루입니다."));
    }

}
