package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CrewAttendances {

    private final List<CrewAttendance> crewAttendances;

    public CrewAttendances(CrewAttendance... crewAttendances) {
        this.crewAttendances = new ArrayList<>(List.of(crewAttendances));
    }

    public CrewAttendances(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public List<CrewAttendance> getCrewAttendances() {
        return Collections.unmodifiableList(crewAttendances);
    }

    public List<AttendanceResult> createAllAttendanceResult(LocalDate endDate) {
        return crewAttendances.stream()
                .map(crewAttendance -> crewAttendance.createAttendanceResult(endDate))
                .toList();
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
