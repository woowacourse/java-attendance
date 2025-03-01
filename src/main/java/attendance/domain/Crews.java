package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.exception.ExceptionMessage;
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
            .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CREW.getMessage(name)));
    }

    public List<Crew> getRiskCrews(LocalDate date) {
        return crews.stream()
            .filter(crew -> crew.getRisk(date) != null)
            .toList();
    }
}
