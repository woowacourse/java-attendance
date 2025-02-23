package attendance.domain;

import static attendance.domain.exception.AttendanceExceptionMessage.ALREADY_ATTENDANCE;
import static attendance.domain.exception.AttendanceExceptionMessage.NOT_IN_ATTENDANCE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private final List<Attendance> attendances;

    public Attendances(LocalDate now, List<LocalDateTime> crewAttendances) {
        List<Attendance> attendances = new ArrayList<>();
        for (LocalDate day = LocalDate.of(now.getYear(), now.getMonth(), 1); day.isBefore(now); day = day.plusDays(1L)) {
            if (isHoliday(day)) {
                continue;
            }
            attendances.add(createAbsenceAttendanceByDate(crewAttendances, day));
        }
        this.attendances = attendances;
    }

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public void existInAttendances(final LocalDate date) {
        attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(date))
                .findAny()
                .ifPresent((attendance) -> {
                    throw new IllegalArgumentException(ALREADY_ATTENDANCE);
                });
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

    public Attendance findAttendanceByDate(final LocalDate updateDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(updateDate))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Attendance updateAttendance(final LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(LocalDate.from(dateTime)))
                .findFirst()
                .map(attendance -> attendance.updateDateTime(dateTime))
                .orElseThrow(() -> new IllegalArgumentException(NOT_IN_ATTENDANCE));
    }

    private Attendance createAbsenceAttendanceByDate(List<LocalDateTime> attendances, LocalDate day) {
        return attendances.stream()
                .filter(attendance -> day.equals(LocalDate.from(attendance)))
                .findFirst()
                .map(Attendance::new)
                .orElse(new Attendance(day.atStartOfDay(), AttendanceStatus.ABSENCE));
    }

    private boolean isHoliday(LocalDate day) {
        return WEEKENDS.contains(day.getDayOfWeek()) || day.equals(CHRISTMAS);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
