package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import util.FileParser;

public class CrewRepository {

    private final List<Crew> crews = new ArrayList<>();

    public static CrewRepository generate() {
        return new CrewRepository();
    }

    public static CrewRepository fromFile() {
        CrewRepository generated = new CrewRepository();
        FileParser.loadAttendanceRecords()
            .forEach(record -> generated.add(
                record.nickname(),
                record.date(),
                record.time()
            ));
        return generated;
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        Crew crew = find(nickname);
        if (crew == null) {
            crew = new Crew(nickname);
            crews.add(crew);
        }
        crew.attendance(date, time);
    }

    private Crew find(String nickname) {
        return crews.stream()
            .filter(crew -> crew.getNickname().equals(nickname))
            .findAny()
            .orElse(null);
    }

    public Crew get(String nickname) {
        Crew found = find(nickname);
        if (found == null) {
            throw new IllegalArgumentException("존재하지 않는 크루명입니다.");
        }
        return found;
    }

    public List<Crew> getAll() {
        return crews;
    }
}
