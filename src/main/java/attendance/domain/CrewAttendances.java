package attendance.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CrewAttendances {

    private final List<CrewAttendance> crewAttendances;

    public CrewAttendances(CrewAttendance... crewAttendances) {
        this.crewAttendances = Arrays.stream(crewAttendances).toList();
    }

    public CrewAttendances(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public List<CrewAttendance> getCrewAttendances() {
        return Collections.unmodifiableList(crewAttendances);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        CrewAttendances that = (CrewAttendances) object;
        return Objects.equals(crewAttendances, that.crewAttendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crewAttendances);
    }
}
