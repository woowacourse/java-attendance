package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Crew {

    private final String name;
    private Map<AttendanceStatus, Integer> attendanceStatuses;

    public Crew(String name) {
        this.name = name;
        this.attendanceStatuses = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatus(AttendanceTimes attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return attendanceStatuses;
    }

    public boolean getExpelStatus(AttendanceTimes attendanceTimes) {
        countAttendanceStatus(attendanceTimes);
        return ExpelStatus.determineExpelStatus(attendanceStatuses) != ExpelStatus.NONE;
    }

    private void countAttendanceStatus(AttendanceTimes attendanceTimes) {
        this.attendanceStatuses = attendanceTimes.calculateAttendanceStatuses();
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
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
