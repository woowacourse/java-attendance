package attendance.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record AttendanceHistory(
        List<AttendanceDateTime> attendanceDateTimes,
        CustomLocalDateTime customLocalDateTime
) {
    public void addAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        attendanceDateTimes.add(attendanceDateTime);
    }

    public AttendanceDateTime findAttendanceDateTime(AttendanceDate attendanceDate) {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.getAttendanceDate().equals(attendanceDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("수정할 출석이 존재하지 않습니다."));
    }

    public long computeLateCount() {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.getAttendanceType() == Attendance.LATE)
                .count();
    }

    public long computeAttendanceCount() {
        return attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.getAttendanceType() == Attendance.ATTEND)
                .count();
    }

    public long computeAbsenceCount() {
         return computeMonthlyAttendableCount() - computeLateCount() - computeAttendanceCount();
    }

    public boolean containsAttendance(AttendanceDate attendanceDate) {
        return attendanceDateTimes.stream()
                .anyMatch(attendanceDateTime -> Objects.equals(
                        attendanceDateTime.getAttendanceDate(),
                        attendanceDate
                ));
    }

    public AttendanceWarning getAttendanceWarning() {
        long absenceCount = computeLateCount() / 3 + computeAbsenceCount();
        if (absenceCount > AttendanceWarning.EXPULSION.getAbsenceCount()) {
            return AttendanceWarning.EXPULSION;
        }
        if (absenceCount >= AttendanceWarning.COUNSELING.getAbsenceCount()) {
            return AttendanceWarning.COUNSELING;
        }
        if (absenceCount >= AttendanceWarning.WARNING.getAbsenceCount()) {
            return AttendanceWarning.WARNING;
        }
        return AttendanceWarning.NONE;
    }

    public LocalDate computeLastAttendableDate() {
        if (customLocalDateTime.nowDate().getYear() > 2024) {
            return LocalDate.of(2024, 12, 31);
        }
        return customLocalDateTime.nowDate();
    }

    public long convertLateCount() {
        return computeAbsenceCount() * 3 + computeLateCount();
    }

    private long computeMonthlyAttendableCount() {
        LocalDate attendanceStartDate = LocalDate.of(2024, 12, 1);
        LocalDate attendanceEndDate = computeLastAttendableDate();
        return Stream.iterate(attendanceStartDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(attendanceStartDate, attendanceEndDate) + 1)
                .filter(WoowaDurationTime::isDurationDate)
                .count();
    }
}
