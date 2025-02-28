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

    public void addAttendance(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        attendances.add(attendance);
    }

    public boolean hasAlreadyAttended(LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.hasSameDate(date)) {
                return true;
            }
        }
        return false;
    }
}
