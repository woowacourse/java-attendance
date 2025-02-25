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
        for (CrewAttendance crewAttendance : crewAttendances) {
            if (crewAttendance.belongsTo(crew)) {
                return crewAttendance;
            }
        }
        throw new IllegalArgumentException("해당하는 이름의 크루가 존재하지 않습니다.");
    }
}
