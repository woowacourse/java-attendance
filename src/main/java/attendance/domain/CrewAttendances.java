package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CrewAttendances {

    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        this.crewAttendances = toCrewAttendances(crewAttendanceDateTimes);
    }

    private Map<Crew, Attendances> toCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        return crewAttendanceDateTimes.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        crew -> new Attendances(crewAttendanceDateTimes.get(crew)
                                .stream()
                                .map(Attendance::new)
                                .toList())
                ));
    }

    public boolean hasCrewAttendanceByLocalDate(final Crew crew, final LocalDate findDate) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
        return crewAttendances.get(crew)
                .hasAttendanceByLocalDate(findDate);
    }

}
