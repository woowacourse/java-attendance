package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AttendanceRecord {
    private final Set<Attendance> value;

    public AttendanceRecord() {
        this.value = new HashSet<>();
    }

    public boolean contains(Attendance targetAttendance) {
        return value.stream()
                .anyMatch(attendance ->
                        attendance.isSameDateWith(targetAttendance));
    }

    public void add(Attendance attendance) {
        value.add(attendance);
    }

    public void modify(Attendance newAttendance) {
        Attendance findAttendance = findSameDateAttendanceBy(newAttendance);
        value.remove(findAttendance);
        value.add(newAttendance);
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

    private Attendance findSameDateAttendanceBy(Attendance targetAttendance) {
        return value.stream()
                .filter(attendance ->
                        attendance.isSameDateWith(targetAttendance))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
