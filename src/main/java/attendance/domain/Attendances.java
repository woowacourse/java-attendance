package attendance.domain;

import java.time.LocalDate;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean add(Attendance attendance) {
        return attendances.add(attendance);
    }

    public Attendance findByCrewNameAndLocalDate(String crewName, LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameLocalDate(crewName, localDate))
                .findFirst()
                .orElse(null);
    }
}
