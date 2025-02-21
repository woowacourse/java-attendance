package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {

    private static List<Attendance> attendances;

    public Attendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public AttendanceDto calculateAttendanceCount() {
        final long attendanceCount = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.ATTENDANCE))
                .count();
        final long tardinessCount = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.TARDINESS))
                .count();
        final long absence = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.ABSENCE))
                .count();

        return new AttendanceDto((int) attendanceCount, (int) tardinessCount, (int) absence);
    }

    public boolean isAttended(final LocalDateTime dateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.localDateTime.equals(dateTime));
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.localDateTime.toLocalDate().equals(dateTime.toLocalDate()))
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

    public void sort() {
        attendances.sort((a, b) -> a.getDateOfMonth() - b.getDateOfMonth());
    }
}
