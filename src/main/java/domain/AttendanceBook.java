package domain;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<CrewAttendance> findDisciplinaryCrews(LocalDate date) {
        List<CrewAttendance> disciplinaryCrews = new ArrayList<>();
        for (CrewAttendance crewAttendance : crewAttendances) {
            int absenceCount = crewAttendance.countAbsenceBeforeDate(date);
            int lateCount = crewAttendance.countLateBeforeDate(date);
            DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.from(absenceCount, lateCount);
            if (disciplinaryStatus != DisciplinaryStatus.NORMAL) {
                disciplinaryCrews.add(crewAttendance);
            }
        }
        return disciplinaryCrews;
    }
}
