package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        crews = new ArrayList<>();
    }

    public void addCrew(final String name) {
        crews.add(new Crew(name));
    }

    public void addAttendStatus(final String name, final LocalDateTime target) {
        Crew crew = findCrewByName(name);
        crew.addAttendStatus(target);
    }

    public void editAttendStatus(final String name, final LocalDateTime target) {
        Crew crew = findCrewByName(name);
        crew.editAttendStatus(target);
    }

    public LocalTime getAttendanceTime(final String name, final LocalDate date) {
        Crew crew = findCrewByName(name);
        return crew.getAttendanceTime(date);
    }

    private Crew findCrewByName(final String name) {
        for (Crew crew : crews) {
            if (crew.isNameMatch(name)) {
                return crew;
            }
        }
        throw new IllegalArgumentException();
    }
}
