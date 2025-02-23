package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Attendances {

    private LinkedList<Attendance> attendances;

    public Attendances(final LinkedList<Attendance> attendances) {
        this.attendances = attendances;
    }

    public AttendanceDto calculateAttendanceCount() {
        final long attendanceCount =getCountByStatus(AttendanceStatus.ATTENDANCE);
        final long tardinessCount = getCountByStatus(AttendanceStatus.TARDINESS);
        final long absence = getCountByStatus(AttendanceStatus.ABSENCE);

        return new AttendanceDto((int) attendanceCount, (int) tardinessCount, (int) absence);
    }

    public int getCountByStatus(AttendanceStatus status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getAttendanceStatus().equals(status))
                .count();
    }

    public boolean isAttended(final LocalDateTime dateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.getLocalDateTime().equals(dateTime));
    }

    public void addSorted(Attendance newAttendance) {
        int index = 0;
        for (Attendance attendance : attendances) {
            if (newAttendance.getLocalDateTime().isBefore(attendance.getLocalDateTime())) {
                break;
            }
            index++;
        }
        attendances.add(index, newAttendance);
    }

    public Attendance findAttendance(final LocalDate finalLocalDate) {
        return attendances.stream()
                .filter(attendance -> attendance.equals(finalLocalDate))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException("수정하는 일자를 찾을 수 없습니다."));
    }

    public List<Integer> getDates() {
        return attendances.stream()
                .map(Attendance::getDateOfMonth)
                .toList();
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void remove(final Attendance oldAttendance) {
        attendances.remove(oldAttendance);
    }
}
