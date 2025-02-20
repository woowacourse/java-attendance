package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import util.FileParser;

public class CrewRepository {

    private final List<Crew> crews = new ArrayList<>();

    public CrewRepository() {
        FileParser.loadAttendanceRecords()
            .forEach(record -> add(
                record.nickname(),
                record.date(),
                record.time()
            ));
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        Crew crew;
        if (!has(nickname)) {
            crew = new Crew(nickname);
            crews.add(crew);
        } else {
            crew = get(nickname);
        }
        crew.attendance(date, time);
    }

    public boolean has(String nickname) {
        return crews.stream()
            .anyMatch(crew -> crew.getNickname().equals(nickname));
    }

    public Crew get(String nickname) {
        return crews.stream()
            .filter(crew -> crew.getNickname().equals(nickname))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루명입니다."));
    }

    public List<Crew> getAll() {
        return crews;
    }
}
