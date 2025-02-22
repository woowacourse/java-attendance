package attendance.domain;

import java.util.Objects;

public class Crew {
    private final String crewName;

    public Crew(String crewName) {
        this.crewName = crewName;
    }

    public boolean isSameCrewName(final String crewName) {
        return this.crewName.equals(crewName);
    }

    public String getName() {
        return crewName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crew that)) {
            return false;
        }
        return Objects.equals(this.crewName, that.crewName);
    }
}
