package attendance.domain;

import static attendance.domain.CrewStatus.NORMAL;
import static attendance.domain.CrewStatus.calculate;

import java.time.LocalDate;
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

    public void calculateDangerousCrews(LocalDate currentDate, AttendanceHistory attendanceHistory) {
        Map<String, AttendanceTimes> attendanceHistories = attendanceHistory.getAttendanceHistory();
        for (String name : attendanceHistories.keySet()) {
            AttendanceTimes attendanceTimes = attendanceHistories.get(name);
            AttendanceResult attendanceResult = AttendanceResult.calculateAttendanceResult(
                currentDate, attendanceTimes);
            ifNotNormalAddDangerousCrew(name, attendanceResult);
        }
    }

    public List<DangerousCrew> getSortedDangerousCrews() {
        sortDangerousCrew();
        return Collections.unmodifiableList(dangerousCrews);
    }

    private void sortDangerousCrew() {
        dangerousCrews.sort(Comparator.comparing(DangerousCrew::getCrewStatus).reversed()
            .thenComparing(DangerousCrew::getNickname));
    }

    private void ifNotNormalAddDangerousCrew(String name, AttendanceResult attendanceResult) {
        CrewStatus crewStatus = calculate(attendanceResult);
        if (crewStatus != NORMAL) {
            DangerousCrew dangerousCrew = DangerousCrew.of(name, crewStatus, attendanceResult);
            addDangerousCrew(dangerousCrew);
        }
    }

    private void addDangerousCrew(DangerousCrew dangerousCrew) {
        dangerousCrews.add(dangerousCrew);
    }
}
