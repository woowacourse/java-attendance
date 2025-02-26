package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AttendanceRecord {
    private final Set<Attendance> value;

    public AttendanceRecord() {
        this.value = new HashSet<>();
    }

    public void add(Attendance attendance) {
        value.add(attendance);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceRecord other = (AttendanceRecord) object;
        return Objects.equals(value, other.value);
    }

    public boolean contains(Attendance targetAttendance) {
        return value.stream()
                .anyMatch(attendance -> attendance.equals(targetAttendance));
    }
}
