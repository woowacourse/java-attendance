package attendance.domain;

import static attendance.constant.ErrorMessage.ALREADY_ATTEND;
import static attendance.constant.ErrorMessage.DATE_WITHOUT_ATTENDANCE;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private static final int LATE_TO_ABSENCE_UNIT = 3;

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addIfAbsent(final Attendance attendance) {
        if (existsByDate(attendance)) {
            throw new IllegalArgumentException(ALREADY_ATTEND.getMessage());
        }
        attendances.add(attendance);
    }

    private boolean existsByDate(final Attendance attendance) {
        return attendances.stream()
                .anyMatch(history -> history.isEqualToDateByAttendance(attendance));
    }

    public Attendance updateAttendance(final LocalDateTime updateDateTime) {
        LocalDate date = LocalDate.from(updateDateTime);
        Attendance attendance = findByDate(date);
        attendance.updateTime(updateDateTime.toLocalTime());
        attendances.set(attendances.indexOf(attendance), attendance);
        return attendance;
    }

    public Attendance findByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(DATE_WITHOUT_ATTENDANCE.getMessage()));
    }

    public AttendancePenalty calculatePenalty() {
        int totalAbsenceCount = countAbsence() + countLate() / LATE_TO_ABSENCE_UNIT;
        return AttendancePenalty.from(totalAbsenceCount);
    }

    public int countAttend() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.ATTEND)
                .count());
    }

    public int countLate() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.LATE)
                .count());
    }

    public int countAbsence() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.ABSENCE)
                .count());
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
