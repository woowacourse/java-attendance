package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<LocalDateTime> attendances) {
        this.attendances = attendances.stream()
                .map(Attendance::new)
                .toList();
    }

    public boolean has(LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(day));
    }

    private Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.has(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    public int countAttendance() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int countTardy() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.TARDY)
                .count();
    }

    public int countAbsence(LocalDate endDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ABSENCE)
                .count();
    }
}
