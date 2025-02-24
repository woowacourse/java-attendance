package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public void registerCrew(String name, LocalDate date, LocalTime time) {
        if (!checkCrewExisted(name)) {
            crews.add(new Crew(name));
        }
        findCrewByName(name).addNewTimeLog(date, time);
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isMyName(name))
                .findAny()
                .orElseThrow();
    }

    public boolean checkCrewExisted(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isMyName(name));
    }
}