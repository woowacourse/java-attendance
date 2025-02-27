package domain;

import java.util.List;
import java.util.Objects;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {
        validateExistAttendance(attendance);
        attendances.add(attendance);
    }

    private void validateExistAttendance(Attendance newAttendance) {
        if (attendances.stream().anyMatch(attendance -> attendance.isSameCrewAndTime(newAttendance))) {
            throw new IllegalArgumentException("출석이 이미 존재합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
