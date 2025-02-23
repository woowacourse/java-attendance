package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Attendances {
    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public Optional<Attendance> findAttendanceByName(String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameName(name))
                .findAny();
    }

    public List<Attendance> findDangerCrew() {
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
