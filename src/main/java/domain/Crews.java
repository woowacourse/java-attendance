package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crews {
    List<Crew> crews;

    public Crews() {
        crews = new ArrayList<>();
    }

    public void addAttendStatus(String name, LocalDateTime target) {
        Crew crew = findCrewByName(name);
        crew.addAttendStatus(target);
    }

    public void editAttendStatus(String name, LocalDateTime target) {
        Crew crew = findCrewByName(name);
        crew.editAttendStatus(target);
    }

    public LocalTime getAttendanceTime(String name, LocalDate date) {
        Crew crew = findCrewByName(name);
        return crew.getAttendanceTime(date);
    }

    private Crew findCrewByName(String name) {
        for (Crew crew : crews) {
            if (crew.isNameMatch(name)) {
                return crew;
            }
        }
        throw new IllegalArgumentException();
    }
}
