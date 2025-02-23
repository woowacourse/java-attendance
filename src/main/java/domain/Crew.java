package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crew {

    private final String name;
    private Map<AttendanceStatus, Integer> attendanceStatuses;

    public Crew(String name) {
        this.name = name;
        this.attendanceStatuses = new HashMap<>();
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatus(AttendanceTimes attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return this.attendanceStatuses;
    }

    public boolean isExpelled(AttendanceTimes attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return ExpelStatus.determineExpelStatus(this.attendanceStatuses) != ExpelStatus.NONE;
    }

    private void countAttendanceStatus(AttendanceTimes attendanceTimes) {
        this.attendanceStatuses = attendanceTimes.calculateAttendanceStatuses();
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(this.name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }
}
