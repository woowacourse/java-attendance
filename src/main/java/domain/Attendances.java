package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attendances {
    private final Map<Crew, Attendance> attendances;

    private Attendances(Map<Crew, Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(Map<Crew, Attendance> attendances) {
        return new Attendances(attendances);
    }

    public Attendance findAttendanceByName(String name) {
        Attendance attendance = attendances.get(Crew.of(name));
        if (attendance == null) {
            throw new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다.");
        }
        return attendance;
    }

    public List<Attendance> findDangerCrews() {
        List<Attendance> dangerAttendances = new ArrayList<>();
        for (Attendance attendance : attendances.values()) {
            int late = attendance.countLate();
            int absence = attendance.countAbsence();
            if (PenaltyStatus.getPenaltyStatus(absence, late) != PenaltyStatus.NONE) {
                dangerAttendances.add(attendance);
            }
        }
        return dangerAttendances;
    }
}
