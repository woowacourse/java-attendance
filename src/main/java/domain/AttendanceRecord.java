package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public Attendance modify(int day, LocalTime newTime) {
        LocalDate date = LocalDate.of(2024, 12, day);
        Attendance findAttendance = value.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        value.remove(findAttendance);
        Attendance newAttendance = new Attendance(LocalDateTime.of(date, newTime));
        value.add(newAttendance);
        return newAttendance;
    }
}
