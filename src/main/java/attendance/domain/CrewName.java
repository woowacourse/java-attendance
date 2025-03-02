package attendance.domain;

import java.util.Objects;

public class CrewName {
    private final String crewName;

    public CrewName(String nameInput) {
        this.crewName = nameInput;
    }

    public String getCrewName() {
        return crewName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrewName that)) {
            return false;
        }
        return Objects.equals(this.crewName, that.crewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewName);
    }
}
