package domain;

import domain.attendance.Attendance;

import java.util.Comparator;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Attendance attendanceRecord;

    public Crew(String name) {
        this.name = name;
        this.attendanceRecord = new Attendance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name) && Objects.equals(attendanceRecord, crew.attendanceRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attendanceRecord);
    }

    public Attendance getAttendanceRecord() {
        return attendanceRecord;
    }
}
