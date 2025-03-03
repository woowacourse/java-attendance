package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean hasAttendanceByLocalDate(final LocalDate findDate) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(findDate));
    }

    public Attendance findSameDateAttendance(final LocalDate findDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(findDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public void modifyByModificationDateTime(final LocalDateTime modificationDateTime) {
        Attendance originAttendance = findSameDateAttendance(modificationDateTime.toLocalDate());
        Attendance modificationAttendance = originAttendance.changeTime(modificationDateTime);
        attendances.remove(originAttendance);
        add(modificationAttendance);
    }

    public void add(final Attendance attendance) {
        validateIsExists(attendance);
        attendances.add(attendance);
    }

    private void validateIsExists(final Attendance addedAttendance) {
        if (attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(addedAttendance))
        ) {
            throw new IllegalArgumentException("해당 날짜의 출석 기록이 이미 존재합니다.");
        }
    }

    public List<Attendance> findAllUntilStandardDate(final LocalDate standardDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isBeforeOrEqualDate(standardDate))
                .toList();
    }

    public int calculateAttendanceCount(final LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBeforeOrEqualDate(standardDate) && attendance.isAttendanceComplete())
                .count();
    }

    public int calculateLateCount(final LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBeforeOrEqualDate(standardDate) && attendance.isLate())
                .count();
    }

    public int calculateAbsentCount(final LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBeforeOrEqualDate(standardDate) && attendance.isAbsence())
                .count();
    }

    public ExpulsionStatus findExpulsionStatusUntilStandardDate(final LocalDate standardDate) {
        int absentCount = calculateAbsentCount(standardDate);
        int lateCount = calculateLateCount(standardDate);
        int totalAbsentCount = AttendanceStatus.calculateTotalAbsentCount(absentCount, lateCount);
        return ExpulsionStatus.findStatusByAbsentCount(totalAbsentCount);
    }

    public List<Attendance> getAscendingAttendances() {
        return attendances.stream()
                .sorted()
                .toList();
    }

}
