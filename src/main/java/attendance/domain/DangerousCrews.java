package attendance.domain;

import static attendance.domain.CrewStatus.CLEAR;
import static attendance.domain.CrewStatus.calculateCrewStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DangerousCrews {

    private final List<DangerousCrew> dangerousCrews;

    private DangerousCrews() {
        this.dangerousCrews = new ArrayList<>();
    }

    public static DangerousCrews create() {
        return new DangerousCrews();
    }

    public List<DangerousCrew> findDangerousCrewsAndSort(
        CrewAttendanceManager crewAttendanceManager, Crews crews) {
        for (Crew crew : crews.getCrews()) {
            addDangerousCrew(crewAttendanceManager, crew);
        }
        sortDangerousCrew();
        return Collections.unmodifiableList(dangerousCrews);
    }

    private void sortDangerousCrew() {
        dangerousCrews.sort(Comparator.comparing(DangerousCrew::getStatusName)
            .thenComparing(DangerousCrew::getCrewName));
    }

    private void addDangerousCrew(CrewAttendanceManager crewAttendanceManager, Crew crew) {
        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        Map<AttendanceType, Long> attendanceResult = attendanceHistories.calculateAttendanceResult();
        CrewStatus crewStatus = calculateCrewStatus(attendanceResult);
        if (crewStatus != CLEAR) {
            DangerousCrew dangerousCrew = DangerousCrew.of(crew, crewStatus, attendanceHistories);
            dangerousCrews.add(dangerousCrew);
        }
    }
}
