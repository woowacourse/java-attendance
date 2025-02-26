package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {

    private final List<Attendance> attendances;

    public AttendanceBook(Attendance... attendance) {
        this.attendances = new ArrayList<>(List.of(attendance));
    }

    public void attend(Attendance newAttendance) {
        if (isAlreadyAttend(newAttendance)) {
            throw new IllegalArgumentException("이미 출석한 경우 다시 출석할 수 없습니다.");
        }
        attendances.add(newAttendance);
    }

    private boolean isAlreadyAttend(Attendance newAttendance) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAlreadyAttend(newAttendance));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceBook that = (AttendanceBook) object;
        return attendances.equals(that.attendances);
    }

    @Override
    public int hashCode() {
        return attendances.hashCode();
    }

    public void updateAttendance(Attendance updateAttendance) {
    }
}
