package attendance.domain;

import java.util.List;
import java.util.Optional;

public class CrewAttendanceRepository {
    private final List<CrewAttendance> crewAttendances;

    public CrewAttendanceRepository(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public CrewAttendance findByName(final String name) {
        Optional<CrewAttendance> targetCrewAttendance = crewAttendances.stream()
                .filter(crewAttendance -> crewAttendance.isNameMatch(name))
                .findAny();

        return targetCrewAttendance.orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 닉네임입니다."));
    }

    public List<CrewAttendance> findByWarningLevel(final WarningLevel warningLevel) {
        return crewAttendances.stream()
                .filter(crewAttendance -> crewAttendance.hasSameWarningLevel(warningLevel))
                .toList();
    }
}
