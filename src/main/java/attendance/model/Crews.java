package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        this.crews = new HashSet<>();
    }

    public void initCrews(List<List<String>> csvData) {
        csvData.stream()
                .map(List::getFirst)
                .distinct()
                .forEach(uniqueCrewName -> crews.add(new Crew(uniqueCrewName))); // Crew 객체 추가
    }

    public void initCrewsAttendance(List<List<String>> csvData) {
        crews.forEach(crew -> crew.initCrewAttendances(csvData));
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.isName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력하신 크루가 존재하지 않습니다."));
    }

    public void attendToday(Crew crew, LocalTime attendTime) {
        crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .ifPresent(findCrew -> findCrew.attendToday(attendTime));
    }

    public Attendance findTodayAttendance(Crew crew) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(findCrew -> findCrew.findAttendance(LocalDate.now()))
                .orElseThrow(() -> new IllegalStateException("출석 체크가 안됐습니다."));
    }

    public boolean hasTodayAttendance(Crew crew) {
        return crews.stream()
                .filter(findCrew -> findCrew.equals(crew))
                .findFirst()
                .map(Crew::isAttendToday)
                .get();
    }
}
