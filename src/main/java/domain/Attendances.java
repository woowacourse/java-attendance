package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Attendances {
    private List<AttendanceRecord> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<AttendanceRecord> attendanceRecords) {
        this.attendances = new ArrayList<>(attendanceRecords);
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

    public AttendanceRecord getAttendanceRecordOfSameDate(AttendanceRecord targetAttendanceRecord) {
        return attendances.stream()
                .filter(attendanceRecord -> attendanceRecord.isSameDate(targetAttendanceRecord))
                .findFirst()
                .orElse(AttendanceRecord.dateOf(targetAttendanceRecord.getDate()
                        .getDayOfMonth()));
    }

    public AttendanceRecord getAttendanceRecordOfSameDate(int date) {
        return attendances.stream()
                .filter(attendanceRecord -> attendanceRecord.isSameDate(date))
                .findFirst()
                .orElse(AttendanceRecord.dateOf(date));
    }

    public Attendances checkAttendance(List<Integer> attendAbleDates) {
        List<AttendanceRecord> result = attendAbleDates.stream()
                .map(this::getAttendanceRecord)
                .collect(Collectors.toList());
        return new Attendances(result);
    }

    private AttendanceRecord getAttendanceRecord(Integer date) {
        return attendances.stream()
                .filter(attendanceRecord -> attendanceRecord.isSameDate(date))
                .findFirst()
                .orElse(AttendanceRecord.dateOf(date));
    }

    public List<AttendanceRecord> getAttendances() {
        return Collections.unmodifiableList(attendances);
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

    @Override
    public String toString() {
        return "Attendances{" + "attendances=" + attendances + '}';
    }
}
