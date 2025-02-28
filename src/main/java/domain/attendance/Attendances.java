package domain.attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean has(LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(day));
    }

    public int countAttendance(LocalDate startDate, LocalDate endDate) {
        return 0;
    }

    public int countTardy(LocalDate startDate, LocalDate endDate) {
        return 0;
    }

    public int countAbsence(LocalDate startDate, LocalDate endDate) {
        return 0;
    }
}
