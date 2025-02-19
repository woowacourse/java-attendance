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
        Crew crew = get(nickname);
        if (crew == null) {
            crew = new Crew(nickname);
            crews.add(crew);
        }
        crew.attendance(date, time);
    }

    public Crew get(String name) {
        return crews.stream()
            .filter(crew -> crew.getName().equals(name))
            .findAny()
            .orElse(null); // TODO 예외처리?
    }
}
