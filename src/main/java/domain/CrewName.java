package domain;

import java.util.Objects;

public class CrewName {

    private final String crewName;

    public CrewName(String crewName) {
        validateCrewName(crewName);
        this.crewName = crewName;
    }

    private void validateCrewName(String crewName) {
        if (crewName.isBlank() || crewName == null) {
            throw new IllegalArgumentException("이름은 빈 값을 넣을 수 없습니다.");
        }
    }

    public String getCrewName() {
        return crewName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewName crewName1 = (CrewName) o;
        return Objects.equals(crewName, crewName1.crewName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crewName);
    }
}
