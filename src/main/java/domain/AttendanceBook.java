package domain;

import java.util.List;

public class AttendanceBook {
    private final List<CrewAttendance> crewAttendances;

    private AttendanceBook(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public static AttendanceBook of(List<CrewAttendance> crewAttendances) {
        return new AttendanceBook(crewAttendances);
    }

    public CrewAttendance findCrewAttendanceByCrew(Crew crew) {
        return crewAttendances.stream()
                .filter(crewAttendance -> crewAttendance.belongsTo(crew))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 이름의 크루가 존재하지 않습니다."));
    }
}
