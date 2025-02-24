package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import util.parser.FileParser;

public class CrewRepository {

    private final List<Crew> crews = new ArrayList<>();
    private boolean unmodifiable = false;

    private CrewRepository() {
    }

    public static CrewRepository generate() {
        return new CrewRepository();
    }

    public static CrewRepository fromFile() {
        CrewRepository generated = new CrewRepository();
        FileParser.loadAttendanceData()
            .forEach(fileData -> generated.add(
                fileData.nickname(),
                fileData.date(),
                fileData.time()
            ));
        generated.unmodifiable = true;
        return generated;
    }

    public void add(Crew crew) {
        validateUnmodifiable();
        crews.add(crew);
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        Crew crew = find(nickname);
        if (crew == null) {
            validateUnmodifiable();
            crew = new Crew(nickname);
            crews.add(crew);
        }
        crew.attendance(date, time);
    }

    private void validateUnmodifiable() {
        if (unmodifiable) {
            throw new IllegalArgumentException("crew를 추가할 수 없습니다");
        }
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
