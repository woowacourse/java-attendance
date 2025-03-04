package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public void updateAttendance(LocalDate date, Attendance attendance) {
        attendances.removeIf(a -> a.hasSameDate(date));
        attendances.add(attendance);
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.hasSameDate(date))
                .findFirst()
                .orElse(null);
    }

    public int countAttendanceByStatus(AttendanceStatus status, LocalDate nowDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.determineStatus() == status)
                .filter(attendance -> attendance.isBeforeDate(nowDate))
                .count();
    }

    public boolean hasAlreadyAttended(LocalDate date) {
        return attendances.stream().anyMatch(attendance -> attendance.hasSameDate(date));
    }
}
