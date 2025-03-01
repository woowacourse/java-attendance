package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String name;
    private final List<Attendance> attendances;

    public Crew(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.attendances = new ArrayList<>();
        attendances.add(new Attendance(date, time));
    }

    public Attendance addAttendance(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        attendances.add(attendance);
        return attendance;
    }

    public Attendance updateAttendance(LocalDate date, LocalTime time) {
        attendances.removeIf(attendance -> attendance.hasSameDate(date));
        return addAttendance(date, time);
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.hasSameDate(date))
                .findFirst()
                .orElseThrow(null);
    }

    public boolean hasAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.hasSameDate(date));
    }
}
