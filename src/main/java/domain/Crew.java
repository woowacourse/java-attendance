package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import java.time.LocalDate;

public class Crew {
    private final String name;
    private final Attendance attendance;

    public Crew(String name) {
        this.name = name;
        this.attendance = new Attendance(
                LocalDate.of(AttendanceDate.DEFAULT_START_TIME, AttendanceDate.DEFAULT_START_MONTH,
                        AttendanceDate.DEFAULT_START_DAY), LocalDate.now());
    }

    public String getName() {
        return this.name;
    }

    public Attendance getAttendance() {
        return attendance;
    }
}
