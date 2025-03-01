package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.util.AttendanceParser;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public static Crews generate() {
        return new Crews(new ArrayList<>());
    }

    public static Crews fromFile() {
        return new Crews(AttendanceParser.parseFile());
    }

    public Crew get(String name) {
        return crews.stream()
            .filter(crew -> crew.getName().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));
    }

    public List<Crew> getRiskCrews(LocalDate date) {
        return crews.stream()
            .filter(crew -> crew.getRisk(date) != null)
            .toList();
    }
}
