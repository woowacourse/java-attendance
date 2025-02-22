package attendance.domain;

import java.util.Objects;

public class Crew {

    private final String crewName;

    private Crew(String crewName) {
        this.crewName = crewName;
    }

    public static Crew from(String crewName) {
        return new Crew(crewName);
    }

    public String getCrewName() {
        return crewName;
    }

    public boolean checkSameName(String crewName) {
        return this.crewName.equals(crewName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return Objects.equals(crewName, crew.crewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewName);
    }

}

