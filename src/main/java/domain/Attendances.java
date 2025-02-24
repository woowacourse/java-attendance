package domain;

import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public Attendance findAttendanceByName(String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameName(name))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다.")
                );
    }

    public List<Attendance> findDangerCrews() {
        List<Attendance> dangerAttendances = new ArrayList<>();
        for (Attendance attendance : attendances) {
            int late = attendance.countLate();
            int absence = attendance.countAbsence();
            if (PenaltyStatus.getPenaltyStatus(absence, late) != PenaltyStatus.NONE) {
                dangerAttendances.add(attendance);
            }
        }
        return dangerAttendances;
    }
}
