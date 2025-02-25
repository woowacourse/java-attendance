package attendance.model;

import java.util.List;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Attendances attendances;

    public Crew(String name) {
        this.name = name;
        this.attendances = new Attendances();
    }

    public void initCrewAttendances(List<List<String>> csvData) {
        attendances.initAttendances(name, csvData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return name.equals(crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }
}
