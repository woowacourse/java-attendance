package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Attendances {
    private List<AttendanceRecord> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void attend(AttendanceRecord attendanceRecord) {
        attendances.add(attendanceRecord);
    }

    public boolean isAttended(AttendanceRecord checkAttendanceRecord) {
        return attendances.stream()
                .anyMatch(attendanceRecord -> attendanceRecord.isSameDate(checkAttendanceRecord));
    }

    public void edit(AttendanceRecord attendanceRecord) {
        removeAttendanceRecordOfSameDate(attendanceRecord);
        attendances.add(attendanceRecord);
    }

    private void removeAttendanceRecordOfSameDate(AttendanceRecord targetAttendanceRecord) {
        attendances = attendances.stream()
                .filter(attendanceRecord -> !attendanceRecord.isSameDate(targetAttendanceRecord))
                .collect(Collectors.toList());
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
