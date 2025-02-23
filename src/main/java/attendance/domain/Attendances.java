package attendance.domain;

import attendance.dto.response.AttendanceGroupByStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public Attendance find(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정하려는 날짜는 출석할 수 없습니다."));
    }

    public Attendance addAttendance(LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        attendances.add(attendance);

        return attendance;
    }

    public Attendance deleteAttendance(LocalDate date) {
        Attendance attendance = find(date);
        attendances.remove(attendance);

        return attendance;
    }

    public List<Attendance> getAttendancesUntilYesterday(LocalDate date) {
        return attendances.stream()
                .sorted(Attendance::compareTo)
                .filter(attendance -> attendance.isBefore(date))
                .toList();
    }

    public AttendanceGroupByStatus createCountUntilYesterday(LocalDate date) {
        int expulsion = calculateStatusUntilYesterday(AttendanceStatusType.EXPULSION, date);
        int late = calculateStatusUntilYesterday(AttendanceStatusType.LATE, date);
        int attendance = calculateStatusUntilYesterday(AttendanceStatusType.ATTENDANCE, date);
        AttendanceWarningType warning = AttendanceWarningType.find(expulsion, late);

        return new AttendanceGroupByStatus(
                expulsion,
                late,
                attendance,
                warning.getName()
        );
    }

    public int size() {
        return attendances.size();
    }

    public void validateAlreadyAttendance(LocalDate date) {
        if (find(date).isAlreadyCheck()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }

    public int calculateStatusUntilYesterday(AttendanceStatusType status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isEqualsStatus(status))
                .count();
    }

    private int calculateStatusUntilYesterday(AttendanceStatusType status, LocalDate date) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(attendance -> attendance.isEqualsStatus(status))
                .count();
    }
}
