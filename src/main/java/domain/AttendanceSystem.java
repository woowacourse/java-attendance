package domain;

import java.time.LocalDate;
import java.util.List;

public class AttendanceSystem {

    private final List<Crew> crews;

    public AttendanceSystem(final List<Crew> crews) {
        this.crews = crews;
    }

    public static AttendanceSystem of(final List<String> data, final LocalDate today) {
        final List<Crew> crews = data.stream()
                .map(d -> d.split(",")[0])
                .distinct()
                .map(d -> Crew.of(d, today))
                .toList();
        data.forEach(d -> updateAttendance(crews, d));
        return new AttendanceSystem(crews);
    }

    private static void updateAttendance(final List<Crew> crews, final String input) {
        final String[] data = input.split(",");
        crews.stream()
                .filter(crew -> crew.isSameName(data[0]))
                .findAny()
                .ifPresent(crew -> crew.updateAttendanceByDateTime(data[1]));
    }

    public Crew findCrewByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
