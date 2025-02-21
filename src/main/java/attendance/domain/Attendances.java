package attendance.domain;

import static attendance.domain.AttendanceStrategy.LATE_TO_ABSENCE_UNIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void existInAttendances(final LocalDate date) {
        attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(date))
                .findAny()
                .ifPresent((attendance) -> {
                    throw new IllegalArgumentException("[ERROR] 이미 오늘 출석을 하셨습니다. 출석 수정을 이용해주세요.");
                });
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Warning checkWarning() {
        return Warning.check(calculateTotalAbsenceCount());
    }

    public long calculateTotalAbsenceCount() {
        return countAbsence() + (countLate() / LATE_TO_ABSENCE_UNIT.getCriteria());
    }

    public long countAttend() {
        return attendances.stream()
                .filter(attendance -> attendance.getStatus().equals(AttendanceStatus.ATTEND))
                .count();
    }

    public long countAbsence() {
        return attendances.stream()
                .filter(attendance -> {
                    AttendanceStatus status = attendance.getStatus();
                    return status.equals(AttendanceStatus.ABSENCE) || status.equals(AttendanceStatus.LATE_ABSENCE);
                })
                .count();
    }

    public long countLate() {
        return attendances.stream()
                .filter(attendance -> attendance.getStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public Attendance updateAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(LocalDate.from(dateTime)))
                .findFirst()
                .map(attendance -> attendance.updateDateTime(dateTime))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(updateDate))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
