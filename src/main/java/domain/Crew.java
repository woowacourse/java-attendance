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
        Attendance originAttendance = findAttendanceByDate(date);
        Attendance newAttendance = new Attendance(date, time);
        if (originAttendance != null) {
            attendances.remove(originAttendance);
        }
        attendances.add(newAttendance);
        return newAttendance;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.hasSameDate(date)) {
                return attendance;
            }
        }
        return null;
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
